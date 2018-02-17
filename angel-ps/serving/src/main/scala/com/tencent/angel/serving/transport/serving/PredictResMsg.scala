/*
 * Tencent is pleased to support the open source community by making Angel available.
 *
 * Copyright (C) 2017 THL A29 Limited, a Tencent company. All rights reserved.
 *
 *  Licensed under the BSD 3-Clause License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 *  https://opensource.org/licenses/BSD-3-Clause
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License
 *  is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  or implied. See the License for the specific language governing permissions and limitations under
 *  the License.
 *
 */

package com.tencent.angel.serving.transport.serving

import com.tencent.angel.serving.common.ModelSplitID
import com.tencent.angel.serving.transport.serving.ServingType.ServingType
import io.netty.buffer.ByteBuf

/**
  * the single predict response message
  * @param id the split ID
  * @param score the score of predict result
  */
class PredictResMsg(val id: ModelSplitID, val score: Double) extends ServingSplitResMsg[Double] {


  override def msgType: ServingType = ServingType.PREDICT_RESPONSE


  override def encodedLength: Int = 8

  override def encode(buf: ByteBuf): Unit = {
    buf.writeDouble(score)
  }

  override def body: Double = score

}

object PredictResMsg {
  def decode(id: ModelSplitID, buf: ByteBuf): PredictResMsg = {
    new PredictResMsg(id, buf.readDouble())

  }
}
