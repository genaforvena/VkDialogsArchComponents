package org.imozerov.vkgroupdialogs.util

import org.imozerov.vkgroupdialogs.db.AppDatabase

inline fun AppDatabase.batchDo(batchUpdates: () -> Unit) {
    try {
        beginTransaction()
        batchUpdates()
        setTransactionSuccessful()
    } finally {
        endTransaction()
    }
}