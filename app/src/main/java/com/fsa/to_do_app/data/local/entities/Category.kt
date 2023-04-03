package com.fsa.to_do_app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Categories")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val colorCode: String,
    val dateCreated: Date = Date()
)

//insert into Categories(name,colorCode, dateCreated)
//values
//( 'Inbox', 'EBEFF5', date()),
//( 'Work', '61DEA4', date()),
//( 'Shopping', 'F45E6D', date()),
//( 'Family', 'FFE761', date()),
//( 'Personal', 'B678FF', date())