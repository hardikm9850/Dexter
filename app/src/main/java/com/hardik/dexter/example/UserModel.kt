/*
 * Created by Hardik on 29/12/23, 5:35 pm
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 29/12/23, 5:35 pm
 *
 */

package com.hardik.dexter.example

import android.os.Parcel
import android.os.Parcelable

data class UserModel(val firstName: String = "Hardik", val lastName: String = "Mehta", val address: String = "Pune") : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(address)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserModel> {
        override fun createFromParcel(parcel: Parcel): UserModel {
            return UserModel(parcel)
        }

        override fun newArray(size: Int): Array<UserModel?> {
            return arrayOfNulls(size)
        }
    }
}
