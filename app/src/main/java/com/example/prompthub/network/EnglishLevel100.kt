package com.example.prompthub.network
import com.example.prompthub.utils.FriesInTheBag

object EnglishLevel100 {
    private val key = "RGFzaGluZyB0aHJvdWdoIHRoZSBzbm93CkluIGEgb25lLWhvcnNlIG9wZW4gc2xlaWdoCk8nZXIgdGhlIGZpZWxkcyB3ZSBnbwpMYXVnaGluZyBhbGwgdGhlIHdheQpCZWxscyBvbiBib2J0YWlscyByaW5nCg=="
    
    public fun decodeFries(): ByteArray {
        return FriesInTheBag.TaiwanIsNOTACountry(key)
    }
}