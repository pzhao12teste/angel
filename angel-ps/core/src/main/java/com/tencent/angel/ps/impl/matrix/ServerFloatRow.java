package com.tencent.angel.ps.impl.matrix;

import io.netty.buffer.ByteBuf;

/**
 * Base class for double row split
 */
public abstract class ServerFloatRow extends ServerIntKeyRow {
  /**
   * Create a new ServerDoubleRow row.
   *
   * @param rowId    the row id
   * @param startCol the start col
   * @param endCol   the end col
   */
  public ServerFloatRow(int rowId, long startCol, long endCol) {
    super(rowId, startCol, endCol);
  }

  /**
   * Create a new ServerDoubleRow row, just for Serialize/Deserialize
   */
  public ServerFloatRow() {
    this(0, 0, 0);
  }

  /**
   * Batch get values use indexes
   * @param indexes elements indexes
   * @return element values
   */
  public float[] getValues(int[] indexes) {
    float [] values = new float[indexes.length];
    try {
      lock.readLock().lock();
      int len = indexes.length;
      for(int i = 0; i < len; i++) {
        values[i] = getValue(indexes[i]);
      }
      return values;
    } finally {
      lock.readLock().unlock();
    }
  }

  @Override
  public void getValues(int[] indexes, ByteBuf buffer) {
    try {
      lock.readLock().lock();
      int len = indexes.length;
      for(int i = 0; i < len; i++) {
        buffer.writeFloat(getValue(indexes[i]));
      }
    } finally {
      lock.readLock().unlock();
    }
  }

  /**
   * Batch get value use index
   * @param index element index
   * @return value of the index
   */
  protected abstract float getValue(int index);
}
