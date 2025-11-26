package com.example.xamaryn.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [XamarynEntity::class], version = 1, exportSchema = false)
class XamarynData {
    abstract class XamarynDatabase: RoomDatabase(){
        abstract fun XamarynDao(): XamarynDao
    }
}