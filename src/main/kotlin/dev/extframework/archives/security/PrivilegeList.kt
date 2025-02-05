@file:JvmName("PrivilegeList")

package dev.extframework.archives.security

import dev.extframework.archives.internal.security.ArchivePermissionPrivilege
import dev.extframework.common.util.UNUSED_INLINE
import java.security.Permission
import java.security.PermissionCollection
import java.security.Permissions

public typealias PrivilegeList = List<ArchivePrivilege>

public typealias MutablePrivilegeList = MutableList<ArchivePrivilege>

@JvmName("toPermissionCollectionExtension") @Suppress(UNUSED_INLINE)
public inline fun PrivilegeList.toPermissionCollection() : PermissionCollection = toPermissionCollection(this)

public fun toPermissionCollection(list: PrivilegeList): PermissionCollection = Permissions().apply {
    class JavaPermissionWrapper(
        val privilege: ArchivePrivilege
    ) : Permission(privilege.name) {
        override fun equals(other: Any?): Boolean = privilege == other

        override fun hashCode(): Int = privilege.hashCode()

        override fun implies(permission: Permission): Boolean = false

        override fun getActions(): String = name

        override fun checkGuard(`object`: Any) : Unit = if (!privilege.checkAccess(`object`)) throw SecurityException("Access to privilege: '$name' denied to '$`object`'") else Unit
    }

    list.forEach {
        add(if (it is ArchivePermissionPrivilege) it.permission else JavaPermissionWrapper(it))
    }
}