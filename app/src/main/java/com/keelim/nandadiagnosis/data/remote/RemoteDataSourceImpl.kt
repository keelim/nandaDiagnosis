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
package com.keelim.nandadiagnosis.data.remote

import com.keelim.nandadiagnosis.data.db.NandaEntity
import com.keelim.nandadiagnosis.service.NandaService
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
  private val service: NandaService
) : RemoteDataSource {
  override suspend fun getNandaInformation(type: String): List<NandaEntity> {
    return service.getNanfaInformation(type)
  }
}