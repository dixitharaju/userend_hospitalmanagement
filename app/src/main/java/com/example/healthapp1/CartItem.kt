package com.example.healthapp1

import android.os.Parcel
import android.os.Parcelable

data class CartItem(
    val name: String,
    var quantity: Int,
    val price: Double
) : Parcelable {

    // Constructor to read from Parcel
    private constructor(parcel: Parcel) : this(
        name = parcel.readString() ?: "",
        quantity = parcel.readInt(),
        price = parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(quantity)
        parcel.writeDouble(price)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<CartItem> {
        override fun createFromParcel(parcel: Parcel): CartItem {
            return CartItem(parcel)
        }

        override fun newArray(size: Int): Array<CartItem?> {
            return arrayOfNulls(size)
        }
    }
}
