package com.modernbank.atm_reporting_service.repository;

import com.modernbank.atm_reporting_service.model.entity.ATM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ATMRepository extends JpaRepository<ATM,String> {

    @Query("SELECT a FROM ATM a WHERE a.id = ?1")
    Optional<ATM> getATMByATMId(String atmId);

    @Query("SELECT a FROM ATM a WHERE (?1 = 'all') or a.id = ?1")
    Optional<List<ATM>> getATMsByLocation(String atmId);

    @Query(value = """
        SELECT
            atm.*,
            ST_Distance_Sphere(
                POINT(atm.latitude, atm.longitude),
                POINT(:userLat, :userLon)
            ) AS mesafe_metre
        FROM
            ATM atm
        HAVING
            mesafe_metre <= 1000
        ORDER BY
            mesafe_metre ASC
    """, nativeQuery = true)
    List<ATM> findNearbyAtmsNative(
            @Param("userLat") double userLatitude,
            @Param("userLon") double userLongitude
    );

    @Query(value = """
        SELECT id
        FROM atm
        ORDER BY ST_Distance_Sphere(
                 POINT(CAST(longitude AS DECIMAL(10,6)), CAST(latitude AS DECIMAL(10,6))),
                 POINT(:userLon, :userLat)
             ) ASC
        LIMIT :limit
        """, nativeQuery = true)
    List<String> findNearestAtmIds(
            @Param("userLat") double userLat,
            @Param("userLon") double userLon,
            @Param("limit") int limit
    );

    // Alternatif: eğer belli bir max mesafe isterseniz:
    @Query(value = """
        SELECT id
        FROM atm
        WHERE ST_Distance_Sphere(
                 POINT(CAST(longitude AS DECIMAL(10,6)), CAST(latitude AS DECIMAL(10,6))),
                 POINT(:userLon, :userLat)
             ) <= :maxMeters
        ORDER BY ST_Distance_Sphere(
                 POINT(CAST(longitude AS DECIMAL(10,6)), CAST(latitude AS DECIMAL(10,6))),
                 POINT(:userLon, :userLat)
             ) ASC
        LIMIT :limit
        """, nativeQuery = true)
    List<String> findNearestAtmIdsWithin(
            @Param("userLat") double userLat,
            @Param("userLon") double userLon,
            @Param("maxMeters") int maxMeters,
            @Param("limit") int limit
    );

}