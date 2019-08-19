package com.whale.android.router.mapping

class RouteMapping(
    var routeType: String,
    var path: String,
    var destination: Class<*>,
    var module: String? = null,
    var requiredParams: Array<String>
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RouteMapping

        if (routeType != other.routeType) return false
        if (path != other.path) return false
        if (destination != other.destination) return false
        if (module != other.module) return false
        if (!requiredParams.contentEquals(other.requiredParams)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = routeType.hashCode()
        result = 31 * result + path.hashCode()
        result = 31 * result + destination.hashCode()
        result = 31 * result + (module?.hashCode() ?: 0)
        result = 31 * result + requiredParams.contentHashCode()
        return result
    }

    fun cloneWithNewPath(path: String): RouteMapping {
        return RouteMapping(routeType, path, destination, module, requiredParams)
    }
}