package fhict.sm.sleepdeprived.data.caffeine.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "caffeine_table")
data class CaffeineEntity(
    @ColumnInfo(name = "intake_moment") val intakeMoment: Long
) {
    @PrimaryKey
    @ColumnInfo(name = "id") var id: UUID = UUID.randomUUID()
}