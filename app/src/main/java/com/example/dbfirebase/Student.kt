package com.example.myapplication

data class Student(
    val name : String ?= null,
    val surname : String ?= null,
    val id : String ?= null,
    val image : String ?= null,
    val email: String ?= null
)