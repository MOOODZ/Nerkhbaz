package ir.moodz.sarafkoochooloo.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ir.moodz.sarafkoochooloo.R

sealed class CurrencyInfo(
    val title: String,
    @StringRes val stringResId: Int,
    @DrawableRes val iconResId: Int? = null,
    val type: CurrencyType,
    val id: Int
) {
    data object IranToman : CurrencyInfo(
        title = "TOMAN",
        stringResId = R.string.toman_currency,
        type = CurrencyType.CURRENCY,
        id = 0,
    )
    data object UnitedStatesDollar : CurrencyInfo(
        title = "USD",
        stringResId = R.string.usd,
        type = CurrencyType.CURRENCY,
        id = 1,
        iconResId = R.drawable.ic_usa
    )
    data object GeorgianLari : CurrencyInfo(
        title = "GEL",
        stringResId = R.string.gel,
        type = CurrencyType.CURRENCY,
        id = 38
    )
    data object ArmenianDram : CurrencyInfo(
        title = "AMD",
        stringResId = R.string.amd,
        type = CurrencyType.CURRENCY,
        id = 36
    )
    data object AzerbaijaniManat : CurrencyInfo(
        title = "AZN",
        stringResId = R.string.azn,
        type = CurrencyType.CURRENCY,
        id = 4
    )
    data object RussianRuble : CurrencyInfo(
        title = "RUB",
        stringResId = R.string.rub,
        type = CurrencyType.CURRENCY,
        id = 5
    )
    data object ThaiBaht : CurrencyInfo(
        title = "THB",
        stringResId = R.string.thb,
        type = CurrencyType.CURRENCY,
        id = 6
    )
    data object MalaysianRinggit : CurrencyInfo(
        title = "MYR",
        stringResId = R.string.myr,
        type = CurrencyType.CURRENCY,
        id = 7
    )
    data object HongKongDollar : CurrencyInfo(
        title = "HKD",
        stringResId = R.string.hkd,
        type = CurrencyType.CURRENCY,
        id = 8
    )
    data object SingaporeDollar : CurrencyInfo(
        title = "SGD",
        stringResId = R.string.sgd,
        type = CurrencyType.CURRENCY,
        id = 9
    )
    data object PakistaniRupee : CurrencyInfo(
        title = "PKR",
        stringResId = R.string.pkr,
        type = CurrencyType.CURRENCY,
        id = 10
    )
    data object IndianRupee : CurrencyInfo(
        title = "INR",
        stringResId = R.string.inr,
        type = CurrencyType.CURRENCY,
        id = 11
    )
    data object SyrianPound : CurrencyInfo(
        title = "SYP",
        stringResId = R.string.syp,
        type = CurrencyType.CURRENCY,
        id = 12
    )
    data object BahrainiDinar : CurrencyInfo(
        title = "BHD",
        stringResId = R.string.bhd,
        type = CurrencyType.CURRENCY,
        id = 13
    )
    data object IraqiDinar : CurrencyInfo(
        title = "IQD",
        stringResId = R.string.iqd,
        type = CurrencyType.CURRENCY,
        id = 14
    )
    data object OmaniRial : CurrencyInfo(
        title = "OMR",
        stringResId = R.string.omr,
        type = CurrencyType.CURRENCY,
        id = 15
    )
    data object QatariRiyal : CurrencyInfo(
        title = "QAR",
        stringResId = R.string.qar,
        type = CurrencyType.CURRENCY,
        id = 16
    )
    data object SaudiRiyal : CurrencyInfo(
        title = "SAR",
        stringResId = R.string.sar,
        type = CurrencyType.CURRENCY,
        id = 17
    )
    data object KuwaitiDinar : CurrencyInfo(
        title = "KWD",
        stringResId = R.string.kwd,
        type = CurrencyType.CURRENCY,
        id = 18
    )
    data object NorwegianKrone : CurrencyInfo(
        title = "NOK",
        stringResId = R.string.nok,
        type = CurrencyType.CURRENCY,
        id = 19
    )
    data object DanishKrone : CurrencyInfo(
        title = "DKK",
        stringResId = R.string.dkk,
        type = CurrencyType.CURRENCY,
        id = 20
    )
    data object SwedishKrona : CurrencyInfo(
        title = "SEK",
        stringResId = R.string.sek,
        type = CurrencyType.CURRENCY,
        id = 21
    )
    data object AfghanAfghani : CurrencyInfo(
        title = "AFN",
        stringResId = R.string.afn,
        type = CurrencyType.CURRENCY,
        id = 22
    )
    data object NewZealandDollar : CurrencyInfo(
        title = "NZD",
        stringResId = R.string.nzd,
        type = CurrencyType.CURRENCY,
        id = 23
    )
    data object AustralianDollar : CurrencyInfo(
        title = "AUD",
        stringResId = R.string.aud,
        type = CurrencyType.CURRENCY,
        id = 24
    )
    data object GeramiGold : CurrencyInfo(
        title = "GERAMI",
        stringResId = R.string.gerami,
        type = CurrencyType.COMMODITY,
        id = 25,
        iconResId = R.drawable.ic_coin
    )
    data object CanadianDollar : CurrencyInfo(
        title = "CAD",
        stringResId = R.string.cad,
        type = CurrencyType.CURRENCY,
        id = 26
    )
    data object RobGold : CurrencyInfo(
        title = "ROB",
        stringResId = R.string.rob,
        type = CurrencyType.COMMODITY,
        id = 27,
        iconResId = R.drawable.ic_coin
    )
    data object JapaneseYen : CurrencyInfo(
        title = "JPY",
        stringResId = R.string.jpy,
        type = CurrencyType.CURRENCY,
        id = 28
    )
    data object NimGold : CurrencyInfo(
        title = "NIM",
        stringResId = R.string.nim,
        type = CurrencyType.COMMODITY,
        id = 29,
        iconResId = R.drawable.ic_coin
    )
    data object ChineseYuan : CurrencyInfo(
        title = "CNY",
        stringResId = R.string.cny,
        type = CurrencyType.CURRENCY,
        id = 30
    )
    data object SekeeEmamiGold : CurrencyInfo(
        title = "SEKEE_EMAMI",
        stringResId = R.string.sekee_emami,
        type = CurrencyType.COMMODITY,
        id = 31,
        iconResId = R.drawable.ic_coin
    )
    data object TurkishLira : CurrencyInfo(
        title = "TRY",
        stringResId = R.string.try_turkey,
        type = CurrencyType.CURRENCY,
        id = 32
    )
    data object SekeBaharGold : CurrencyInfo(
        title = "SEKE_BAHAR",
        stringResId = R.string.seke_bahar,
        type = CurrencyType.COMMODITY,
        id = 33,
        iconResId = R.drawable.ic_coin
    )
    data object UnitedArabEmiratesDirham : CurrencyInfo(
        title = "AED",
        stringResId = R.string.aed,
        type = CurrencyType.CURRENCY,
        id = 34
    )
    data object OunceGold : CurrencyInfo(
        title = "ONS",
        stringResId = R.string.ons,
        type = CurrencyType.COMMODITY,
        id = 35,
        iconResId = R.drawable.ic_gold
    )
    data object BritishPound : CurrencyInfo(
        title = "GBP",
        stringResId = R.string.gbp,
        type = CurrencyType.CURRENCY,
        id = 3,
        iconResId = R.drawable.ic_england
    )
    data object Gerami18Gold : CurrencyInfo(
        title = "GERAMI_18",
        stringResId = R.string.gerami_18,
        type = CurrencyType.COMMODITY,
        id = 37,
        iconResId = R.drawable.ic_gold
    )
    data object Euro : CurrencyInfo(
        title = "EUR",
        stringResId = R.string.eur,
        type = CurrencyType.CURRENCY,
        id = 2,
        iconResId = R.drawable.ic_eu
    )
    data object MesghalGold : CurrencyInfo(
        title = "MESGHAL",
        stringResId = R.string.mesghal,
        type = CurrencyType.COMMODITY,
        id =39,
        iconResId = R.drawable.ic_gold
    )
    data object Gerami24Gold : CurrencyInfo(
        title = "GERAMI_24",
        stringResId = R.string.gerami_24,
        type = CurrencyType.COMMODITY,
        id = 40,
        iconResId = R.drawable.ic_gold
    )

    companion object {
        fun fromTitle(title: String): CurrencyInfo {
            return when (title.uppercase()) {
                UnitedStatesDollar.title -> UnitedStatesDollar
                IranToman.title -> IranToman
                GeorgianLari.title -> GeorgianLari
                ArmenianDram.title -> ArmenianDram
                AzerbaijaniManat.title -> AzerbaijaniManat
                RussianRuble.title -> RussianRuble
                ThaiBaht.title -> ThaiBaht
                MalaysianRinggit.title -> MalaysianRinggit
                HongKongDollar.title -> HongKongDollar
                SingaporeDollar.title -> SingaporeDollar
                PakistaniRupee.title -> PakistaniRupee
                IndianRupee.title -> IndianRupee
                SyrianPound.title -> SyrianPound
                BahrainiDinar.title -> BahrainiDinar
                IraqiDinar.title -> IraqiDinar
                OmaniRial.title -> OmaniRial
                QatariRiyal.title -> QatariRiyal
                SaudiRiyal.title -> SaudiRiyal
                KuwaitiDinar.title -> KuwaitiDinar
                NorwegianKrone.title -> NorwegianKrone
                DanishKrone.title -> DanishKrone
                SwedishKrona.title -> SwedishKrona
                AfghanAfghani.title -> AfghanAfghani
                NewZealandDollar.title -> NewZealandDollar
                AustralianDollar.title -> AustralianDollar
                GeramiGold.title -> GeramiGold
                CanadianDollar.title -> CanadianDollar
                RobGold.title -> RobGold
                JapaneseYen.title -> JapaneseYen
                NimGold.title -> NimGold
                ChineseYuan.title -> ChineseYuan
                SekeeEmamiGold.title -> SekeeEmamiGold
                TurkishLira.title -> TurkishLira
                SekeBaharGold.title -> SekeBaharGold
                UnitedArabEmiratesDirham.title -> UnitedArabEmiratesDirham
                OunceGold.title -> OunceGold
                BritishPound.title -> BritishPound
                Gerami18Gold.title -> Gerami18Gold
                Euro.title -> Euro
                MesghalGold.title -> MesghalGold
                Gerami24Gold.title -> Gerami24Gold
                else -> UnitedStatesDollar
            }
        }
        fun fromInt(id: Int): CurrencyInfo {
            return when (id) {
                IranToman.id -> IranToman
                UnitedStatesDollar.id -> UnitedStatesDollar
                GeorgianLari.id -> GeorgianLari
                ArmenianDram.id -> ArmenianDram
                AzerbaijaniManat.id -> AzerbaijaniManat
                RussianRuble.id -> RussianRuble
                ThaiBaht.id -> ThaiBaht
                MalaysianRinggit.id -> MalaysianRinggit
                HongKongDollar.id -> HongKongDollar
                SingaporeDollar.id -> SingaporeDollar
                PakistaniRupee.id -> PakistaniRupee
                IndianRupee.id -> IndianRupee
                SyrianPound.id -> SyrianPound
                BahrainiDinar.id -> BahrainiDinar
                IraqiDinar.id -> IraqiDinar
                OmaniRial.id -> OmaniRial
                QatariRiyal.id -> QatariRiyal
                SaudiRiyal.id -> SaudiRiyal
                KuwaitiDinar.id -> KuwaitiDinar
                NorwegianKrone.id -> NorwegianKrone
                DanishKrone.id -> DanishKrone
                SwedishKrona.id -> SwedishKrona
                AfghanAfghani.id -> AfghanAfghani
                NewZealandDollar.id -> NewZealandDollar
                AustralianDollar.id -> AustralianDollar
                GeramiGold.id -> GeramiGold
                CanadianDollar.id -> CanadianDollar
                RobGold.id -> RobGold
                JapaneseYen.id -> JapaneseYen
                NimGold.id -> NimGold
                ChineseYuan.id -> ChineseYuan
                SekeeEmamiGold.id -> SekeeEmamiGold
                TurkishLira.id -> TurkishLira
                SekeBaharGold.id -> SekeBaharGold
                UnitedArabEmiratesDirham.id -> UnitedArabEmiratesDirham
                OunceGold.id -> OunceGold
                BritishPound.id -> BritishPound
                Gerami18Gold.id -> Gerami18Gold
                Euro.id -> Euro
                MesghalGold.id -> MesghalGold
                Gerami24Gold.id -> Gerami24Gold
                else -> UnitedStatesDollar
            }
        }
    }
}