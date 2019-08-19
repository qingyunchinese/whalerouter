package com.whale.android.router.processor

class UniqueValueList {

    private val uniqueValueList = arrayListOf<String>()

    fun add(value: String) {
        if (!uniqueValueList.contains(value)) {
            uniqueValueList.add(value)
        }
        uniqueValueList.forEach {  }
    }

    fun reverse() {
        uniqueValueList.reverse()
    }

    fun forEach(action: (String) -> Unit){
        uniqueValueList.forEach(action)
    }

    fun plusLast(moduleName: String) {
        uniqueValueList.add(0, moduleName)
    }
}