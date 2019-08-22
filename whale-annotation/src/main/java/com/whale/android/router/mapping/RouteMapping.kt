package com.whale.android.router.mapping

class RouteMapping(
    var routeComponentType: String,
    var path: String,
    var destination: Class<*>,
    var module: String,
    var requiredParams: Array<String>,
    var requiredAuthor: Boolean
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RouteMapping

        if (routeComponentType != other.routeComponentType) return false
        if (path != other.path) return false
        if (destination != other.destination) return false
        if (module != other.module) return false
        if (!requiredParams.contentEquals(other.requiredParams)) return false
        if (requiredAuthor != other.requiredAuthor) return false
        return true
    }

    override fun hashCode(): Int {
        var result = routeComponentType.hashCode()
        result = 31 * result + path.hashCode()
        result = 31 * result + destination.hashCode()
        result = 31 * result + (module.hashCode())
        result = 31 * result + (requiredAuthor.hashCode())
        result = 31 * result + requiredParams.contentHashCode()
        return result
    }

    fun cloneWithNewPath(path: String): RouteMapping {
        return RouteMapping(routeComponentType, path, destination, module, requiredParams, requiredAuthor)
    }
}