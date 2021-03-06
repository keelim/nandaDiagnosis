/*
 * Designed and developed by 2020 keelim (Jaehyun Kim)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.keelim.nandadiagnosis.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.keelim.nandadiagnosis.data.db.entity.NandaEntity

@Dao
interface
DataDaoV2 {
  @Query("select * from nanda where reason like  '%' || :keyword || '%'")
  suspend fun search(keyword: String?): List<NandaEntity>

  @Query("SELECT * FROM nanda WHERE favorite = 1")
  suspend fun favorites(): List<NandaEntity>

  @Query("select * from nanda where category = :number")
  suspend fun get(number: Int?): List<NandaEntity>

  @Query("select * from nanda order by nanda_id desc")
  suspend fun getNanda(): NandaEntity?

  @Query("select * from nanda")
  suspend fun getAll(): List<NandaEntity>

  @Insert
  suspend fun insertNanda(nanda: NandaEntity)

  @Query("delete  from nanda where reason ==:keyword")
  suspend fun delete(keyword: String?)

  @Query("UPDATE nanda SET favorite=:favorite WHERE nanda_id = :id")
  suspend fun favoriteUpdate(favorite: Int, id: Int)
}
