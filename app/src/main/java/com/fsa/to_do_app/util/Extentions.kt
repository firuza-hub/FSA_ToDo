package com.fsa.to_do_app.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


fun Date.getTimeShort(): String = SimpleDateFormat.getTimeInstance(DateFormat.SHORT).format(this)