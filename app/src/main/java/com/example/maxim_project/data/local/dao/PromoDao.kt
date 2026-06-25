package com.example.maxim_project.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.maxim_project.data.model.Promo
import kotlinx.coroutines.flow.Flow

@Dao
interface PromoDao {
    @Query("SELECT * FROM promos")
    fun getAllPromos(): Flow<List<Promo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPromo(promo: Promo)
}
