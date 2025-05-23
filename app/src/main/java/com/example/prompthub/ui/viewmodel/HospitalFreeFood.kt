package com.example.prompthub.ui.viewmodel
import com.example.prompthub.utils.FriesInTheBag

object HospitalFreeFood {
    private val key = "WW91QXJlTm90QnVpbHRGb3JUaGlzR29vZEx1Y2tQYWxJdHNPdmVyV2l0aFlvdUFyZU5vdEJ1aWx0Rm9yVGhpc0dvb2RMdWNrUGFsSXRzT3ZlcldpdGhZb3VBcmVOb3RCdWlsdEZvclRoaXNHb29kTHVja1BhbEl0c092ZXJXaXRo"
    
    public fun decodeFries(): ByteArray {
        return FriesInTheBag.TaiwanIsNOTACountry(key)
    }
}