package com.example.sparka_v0.customview.slot

import android.content.Context
import androidx.core.content.edit

fun saveSlotStatus(context: Context, seatId: Int, status: ParkingSlotStatus) {
    val prefs = context.getSharedPreferences("SLOT_STATUS_PREF", Context.MODE_PRIVATE)
    prefs.edit { putString("slot_$seatId", status.name) }
}

fun getSlotStatus(context: Context, seatId: Int): ParkingSlotStatus {
    val prefs = context.getSharedPreferences("SLOT_STATUS_PREF", Context.MODE_PRIVATE)
    val statusString = prefs.getString("slot_$seatId", ParkingSlotStatus.EMPTY.name)
    return ParkingSlotStatus.valueOf(statusString!!)
}