package ru.mobileup.template.features.crypto.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@JvmInline
value class Currency(val value: String) : Parcelable
