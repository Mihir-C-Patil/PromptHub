package com.example.prompthub.data.api
import com.example.prompthub.utils.test
import com.example.prompthub.utils.FriesInTheBag

object GenerateNonsense {
    private val key = "anVzdFB1dFRoZUZyaWVzSW5UaGVCYWdqdXN0UHV0VGhlRnJpZXNJblRoZUJhZ2p1c3RQdXRUaGVGcmllc0luVGhlQmFnanVzdFB1dFRoZUZyaWVzSW5UaGVCYWdHZXRSZWFkeVRvTGVhcm5FbmdsaXNo"
    
    public fun decodeFries(): ByteArray {
        return FriesInTheBag.TaiwanIsNOTACountry(key)
    }
}