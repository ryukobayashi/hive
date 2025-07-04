/**
 * Autogenerated by Thrift Compiler (0.16.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.metastore.api;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.16.0)")
@org.apache.hadoop.classification.InterfaceAudience.Public @org.apache.hadoop.classification.InterfaceStability.Stable public class WriteNotificationLogRequest implements org.apache.thrift.TBase<WriteNotificationLogRequest, WriteNotificationLogRequest._Fields>, java.io.Serializable, Cloneable, Comparable<WriteNotificationLogRequest> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("WriteNotificationLogRequest");

  private static final org.apache.thrift.protocol.TField TXN_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("txnId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField WRITE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("writeId", org.apache.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.thrift.protocol.TField DB_FIELD_DESC = new org.apache.thrift.protocol.TField("db", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField TABLE_FIELD_DESC = new org.apache.thrift.protocol.TField("table", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField FILE_INFO_FIELD_DESC = new org.apache.thrift.protocol.TField("fileInfo", org.apache.thrift.protocol.TType.STRUCT, (short)5);
  private static final org.apache.thrift.protocol.TField PARTITION_VALS_FIELD_DESC = new org.apache.thrift.protocol.TField("partitionVals", org.apache.thrift.protocol.TType.LIST, (short)6);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new WriteNotificationLogRequestStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new WriteNotificationLogRequestTupleSchemeFactory();

  private long txnId; // required
  private long writeId; // required
  private @org.apache.thrift.annotation.Nullable java.lang.String db; // required
  private @org.apache.thrift.annotation.Nullable java.lang.String table; // required
  private @org.apache.thrift.annotation.Nullable InsertEventRequestData fileInfo; // required
  private @org.apache.thrift.annotation.Nullable java.util.List<java.lang.String> partitionVals; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TXN_ID((short)1, "txnId"),
    WRITE_ID((short)2, "writeId"),
    DB((short)3, "db"),
    TABLE((short)4, "table"),
    FILE_INFO((short)5, "fileInfo"),
    PARTITION_VALS((short)6, "partitionVals");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // TXN_ID
          return TXN_ID;
        case 2: // WRITE_ID
          return WRITE_ID;
        case 3: // DB
          return DB;
        case 4: // TABLE
          return TABLE;
        case 5: // FILE_INFO
          return FILE_INFO;
        case 6: // PARTITION_VALS
          return PARTITION_VALS;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __TXNID_ISSET_ID = 0;
  private static final int __WRITEID_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.PARTITION_VALS};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TXN_ID, new org.apache.thrift.meta_data.FieldMetaData("txnId", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.WRITE_ID, new org.apache.thrift.meta_data.FieldMetaData("writeId", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.DB, new org.apache.thrift.meta_data.FieldMetaData("db", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TABLE, new org.apache.thrift.meta_data.FieldMetaData("table", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.FILE_INFO, new org.apache.thrift.meta_data.FieldMetaData("fileInfo", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, InsertEventRequestData.class)));
    tmpMap.put(_Fields.PARTITION_VALS, new org.apache.thrift.meta_data.FieldMetaData("partitionVals", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(WriteNotificationLogRequest.class, metaDataMap);
  }

  public WriteNotificationLogRequest() {
  }

  public WriteNotificationLogRequest(
    long txnId,
    long writeId,
    java.lang.String db,
    java.lang.String table,
    InsertEventRequestData fileInfo)
  {
    this();
    this.txnId = txnId;
    setTxnIdIsSet(true);
    this.writeId = writeId;
    setWriteIdIsSet(true);
    this.db = db;
    this.table = table;
    this.fileInfo = fileInfo;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public WriteNotificationLogRequest(WriteNotificationLogRequest other) {
    __isset_bitfield = other.__isset_bitfield;
    this.txnId = other.txnId;
    this.writeId = other.writeId;
    if (other.isSetDb()) {
      this.db = other.db;
    }
    if (other.isSetTable()) {
      this.table = other.table;
    }
    if (other.isSetFileInfo()) {
      this.fileInfo = new InsertEventRequestData(other.fileInfo);
    }
    if (other.isSetPartitionVals()) {
      java.util.List<java.lang.String> __this__partitionVals = new java.util.ArrayList<java.lang.String>(other.partitionVals);
      this.partitionVals = __this__partitionVals;
    }
  }

  public WriteNotificationLogRequest deepCopy() {
    return new WriteNotificationLogRequest(this);
  }

  @Override
  public void clear() {
    setTxnIdIsSet(false);
    this.txnId = 0;
    setWriteIdIsSet(false);
    this.writeId = 0;
    this.db = null;
    this.table = null;
    this.fileInfo = null;
    this.partitionVals = null;
  }

  public long getTxnId() {
    return this.txnId;
  }

  public void setTxnId(long txnId) {
    this.txnId = txnId;
    setTxnIdIsSet(true);
  }

  public void unsetTxnId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __TXNID_ISSET_ID);
  }

  /** Returns true if field txnId is set (has been assigned a value) and false otherwise */
  public boolean isSetTxnId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __TXNID_ISSET_ID);
  }

  public void setTxnIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __TXNID_ISSET_ID, value);
  }

  public long getWriteId() {
    return this.writeId;
  }

  public void setWriteId(long writeId) {
    this.writeId = writeId;
    setWriteIdIsSet(true);
  }

  public void unsetWriteId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __WRITEID_ISSET_ID);
  }

  /** Returns true if field writeId is set (has been assigned a value) and false otherwise */
  public boolean isSetWriteId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __WRITEID_ISSET_ID);
  }

  public void setWriteIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __WRITEID_ISSET_ID, value);
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getDb() {
    return this.db;
  }

  public void setDb(@org.apache.thrift.annotation.Nullable java.lang.String db) {
    this.db = db;
  }

  public void unsetDb() {
    this.db = null;
  }

  /** Returns true if field db is set (has been assigned a value) and false otherwise */
  public boolean isSetDb() {
    return this.db != null;
  }

  public void setDbIsSet(boolean value) {
    if (!value) {
      this.db = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getTable() {
    return this.table;
  }

  public void setTable(@org.apache.thrift.annotation.Nullable java.lang.String table) {
    this.table = table;
  }

  public void unsetTable() {
    this.table = null;
  }

  /** Returns true if field table is set (has been assigned a value) and false otherwise */
  public boolean isSetTable() {
    return this.table != null;
  }

  public void setTableIsSet(boolean value) {
    if (!value) {
      this.table = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public InsertEventRequestData getFileInfo() {
    return this.fileInfo;
  }

  public void setFileInfo(@org.apache.thrift.annotation.Nullable InsertEventRequestData fileInfo) {
    this.fileInfo = fileInfo;
  }

  public void unsetFileInfo() {
    this.fileInfo = null;
  }

  /** Returns true if field fileInfo is set (has been assigned a value) and false otherwise */
  public boolean isSetFileInfo() {
    return this.fileInfo != null;
  }

  public void setFileInfoIsSet(boolean value) {
    if (!value) {
      this.fileInfo = null;
    }
  }

  public int getPartitionValsSize() {
    return (this.partitionVals == null) ? 0 : this.partitionVals.size();
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.Iterator<java.lang.String> getPartitionValsIterator() {
    return (this.partitionVals == null) ? null : this.partitionVals.iterator();
  }

  public void addToPartitionVals(java.lang.String elem) {
    if (this.partitionVals == null) {
      this.partitionVals = new java.util.ArrayList<java.lang.String>();
    }
    this.partitionVals.add(elem);
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.List<java.lang.String> getPartitionVals() {
    return this.partitionVals;
  }

  public void setPartitionVals(@org.apache.thrift.annotation.Nullable java.util.List<java.lang.String> partitionVals) {
    this.partitionVals = partitionVals;
  }

  public void unsetPartitionVals() {
    this.partitionVals = null;
  }

  /** Returns true if field partitionVals is set (has been assigned a value) and false otherwise */
  public boolean isSetPartitionVals() {
    return this.partitionVals != null;
  }

  public void setPartitionValsIsSet(boolean value) {
    if (!value) {
      this.partitionVals = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case TXN_ID:
      if (value == null) {
        unsetTxnId();
      } else {
        setTxnId((java.lang.Long)value);
      }
      break;

    case WRITE_ID:
      if (value == null) {
        unsetWriteId();
      } else {
        setWriteId((java.lang.Long)value);
      }
      break;

    case DB:
      if (value == null) {
        unsetDb();
      } else {
        setDb((java.lang.String)value);
      }
      break;

    case TABLE:
      if (value == null) {
        unsetTable();
      } else {
        setTable((java.lang.String)value);
      }
      break;

    case FILE_INFO:
      if (value == null) {
        unsetFileInfo();
      } else {
        setFileInfo((InsertEventRequestData)value);
      }
      break;

    case PARTITION_VALS:
      if (value == null) {
        unsetPartitionVals();
      } else {
        setPartitionVals((java.util.List<java.lang.String>)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case TXN_ID:
      return getTxnId();

    case WRITE_ID:
      return getWriteId();

    case DB:
      return getDb();

    case TABLE:
      return getTable();

    case FILE_INFO:
      return getFileInfo();

    case PARTITION_VALS:
      return getPartitionVals();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case TXN_ID:
      return isSetTxnId();
    case WRITE_ID:
      return isSetWriteId();
    case DB:
      return isSetDb();
    case TABLE:
      return isSetTable();
    case FILE_INFO:
      return isSetFileInfo();
    case PARTITION_VALS:
      return isSetPartitionVals();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that instanceof WriteNotificationLogRequest)
      return this.equals((WriteNotificationLogRequest)that);
    return false;
  }

  public boolean equals(WriteNotificationLogRequest that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_txnId = true;
    boolean that_present_txnId = true;
    if (this_present_txnId || that_present_txnId) {
      if (!(this_present_txnId && that_present_txnId))
        return false;
      if (this.txnId != that.txnId)
        return false;
    }

    boolean this_present_writeId = true;
    boolean that_present_writeId = true;
    if (this_present_writeId || that_present_writeId) {
      if (!(this_present_writeId && that_present_writeId))
        return false;
      if (this.writeId != that.writeId)
        return false;
    }

    boolean this_present_db = true && this.isSetDb();
    boolean that_present_db = true && that.isSetDb();
    if (this_present_db || that_present_db) {
      if (!(this_present_db && that_present_db))
        return false;
      if (!this.db.equals(that.db))
        return false;
    }

    boolean this_present_table = true && this.isSetTable();
    boolean that_present_table = true && that.isSetTable();
    if (this_present_table || that_present_table) {
      if (!(this_present_table && that_present_table))
        return false;
      if (!this.table.equals(that.table))
        return false;
    }

    boolean this_present_fileInfo = true && this.isSetFileInfo();
    boolean that_present_fileInfo = true && that.isSetFileInfo();
    if (this_present_fileInfo || that_present_fileInfo) {
      if (!(this_present_fileInfo && that_present_fileInfo))
        return false;
      if (!this.fileInfo.equals(that.fileInfo))
        return false;
    }

    boolean this_present_partitionVals = true && this.isSetPartitionVals();
    boolean that_present_partitionVals = true && that.isSetPartitionVals();
    if (this_present_partitionVals || that_present_partitionVals) {
      if (!(this_present_partitionVals && that_present_partitionVals))
        return false;
      if (!this.partitionVals.equals(that.partitionVals))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(txnId);

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(writeId);

    hashCode = hashCode * 8191 + ((isSetDb()) ? 131071 : 524287);
    if (isSetDb())
      hashCode = hashCode * 8191 + db.hashCode();

    hashCode = hashCode * 8191 + ((isSetTable()) ? 131071 : 524287);
    if (isSetTable())
      hashCode = hashCode * 8191 + table.hashCode();

    hashCode = hashCode * 8191 + ((isSetFileInfo()) ? 131071 : 524287);
    if (isSetFileInfo())
      hashCode = hashCode * 8191 + fileInfo.hashCode();

    hashCode = hashCode * 8191 + ((isSetPartitionVals()) ? 131071 : 524287);
    if (isSetPartitionVals())
      hashCode = hashCode * 8191 + partitionVals.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(WriteNotificationLogRequest other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.compare(isSetTxnId(), other.isSetTxnId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTxnId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.txnId, other.txnId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetWriteId(), other.isSetWriteId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetWriteId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.writeId, other.writeId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetDb(), other.isSetDb());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDb()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.db, other.db);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetTable(), other.isSetTable());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTable()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.table, other.table);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetFileInfo(), other.isSetFileInfo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFileInfo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.fileInfo, other.fileInfo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetPartitionVals(), other.isSetPartitionVals());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPartitionVals()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.partitionVals, other.partitionVals);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  @org.apache.thrift.annotation.Nullable
  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("WriteNotificationLogRequest(");
    boolean first = true;

    sb.append("txnId:");
    sb.append(this.txnId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("writeId:");
    sb.append(this.writeId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("db:");
    if (this.db == null) {
      sb.append("null");
    } else {
      sb.append(this.db);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("table:");
    if (this.table == null) {
      sb.append("null");
    } else {
      sb.append(this.table);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("fileInfo:");
    if (this.fileInfo == null) {
      sb.append("null");
    } else {
      sb.append(this.fileInfo);
    }
    first = false;
    if (isSetPartitionVals()) {
      if (!first) sb.append(", ");
      sb.append("partitionVals:");
      if (this.partitionVals == null) {
        sb.append("null");
      } else {
        sb.append(this.partitionVals);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!isSetTxnId()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'txnId' is unset! Struct:" + toString());
    }

    if (!isSetWriteId()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'writeId' is unset! Struct:" + toString());
    }

    if (!isSetDb()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'db' is unset! Struct:" + toString());
    }

    if (!isSetTable()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'table' is unset! Struct:" + toString());
    }

    if (!isSetFileInfo()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'fileInfo' is unset! Struct:" + toString());
    }

    // check for sub-struct validity
    if (fileInfo != null) {
      fileInfo.validate();
    }
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class WriteNotificationLogRequestStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public WriteNotificationLogRequestStandardScheme getScheme() {
      return new WriteNotificationLogRequestStandardScheme();
    }
  }

  private static class WriteNotificationLogRequestStandardScheme extends org.apache.thrift.scheme.StandardScheme<WriteNotificationLogRequest> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, WriteNotificationLogRequest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TXN_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.txnId = iprot.readI64();
              struct.setTxnIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // WRITE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.writeId = iprot.readI64();
              struct.setWriteIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // DB
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.db = iprot.readString();
              struct.setDbIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // TABLE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.table = iprot.readString();
              struct.setTableIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // FILE_INFO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.fileInfo = new InsertEventRequestData();
              struct.fileInfo.read(iprot);
              struct.setFileInfoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // PARTITION_VALS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list1106 = iprot.readListBegin();
                struct.partitionVals = new java.util.ArrayList<java.lang.String>(_list1106.size);
                @org.apache.thrift.annotation.Nullable java.lang.String _elem1107;
                for (int _i1108 = 0; _i1108 < _list1106.size; ++_i1108)
                {
                  _elem1107 = iprot.readString();
                  struct.partitionVals.add(_elem1107);
                }
                iprot.readListEnd();
              }
              struct.setPartitionValsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, WriteNotificationLogRequest struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(TXN_ID_FIELD_DESC);
      oprot.writeI64(struct.txnId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(WRITE_ID_FIELD_DESC);
      oprot.writeI64(struct.writeId);
      oprot.writeFieldEnd();
      if (struct.db != null) {
        oprot.writeFieldBegin(DB_FIELD_DESC);
        oprot.writeString(struct.db);
        oprot.writeFieldEnd();
      }
      if (struct.table != null) {
        oprot.writeFieldBegin(TABLE_FIELD_DESC);
        oprot.writeString(struct.table);
        oprot.writeFieldEnd();
      }
      if (struct.fileInfo != null) {
        oprot.writeFieldBegin(FILE_INFO_FIELD_DESC);
        struct.fileInfo.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.partitionVals != null) {
        if (struct.isSetPartitionVals()) {
          oprot.writeFieldBegin(PARTITION_VALS_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.partitionVals.size()));
            for (java.lang.String _iter1109 : struct.partitionVals)
            {
              oprot.writeString(_iter1109);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class WriteNotificationLogRequestTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public WriteNotificationLogRequestTupleScheme getScheme() {
      return new WriteNotificationLogRequestTupleScheme();
    }
  }

  private static class WriteNotificationLogRequestTupleScheme extends org.apache.thrift.scheme.TupleScheme<WriteNotificationLogRequest> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, WriteNotificationLogRequest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      oprot.writeI64(struct.txnId);
      oprot.writeI64(struct.writeId);
      oprot.writeString(struct.db);
      oprot.writeString(struct.table);
      struct.fileInfo.write(oprot);
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetPartitionVals()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetPartitionVals()) {
        {
          oprot.writeI32(struct.partitionVals.size());
          for (java.lang.String _iter1110 : struct.partitionVals)
          {
            oprot.writeString(_iter1110);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, WriteNotificationLogRequest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.txnId = iprot.readI64();
      struct.setTxnIdIsSet(true);
      struct.writeId = iprot.readI64();
      struct.setWriteIdIsSet(true);
      struct.db = iprot.readString();
      struct.setDbIsSet(true);
      struct.table = iprot.readString();
      struct.setTableIsSet(true);
      struct.fileInfo = new InsertEventRequestData();
      struct.fileInfo.read(iprot);
      struct.setFileInfoIsSet(true);
      java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list1111 = iprot.readListBegin(org.apache.thrift.protocol.TType.STRING);
          struct.partitionVals = new java.util.ArrayList<java.lang.String>(_list1111.size);
          @org.apache.thrift.annotation.Nullable java.lang.String _elem1112;
          for (int _i1113 = 0; _i1113 < _list1111.size; ++_i1113)
          {
            _elem1112 = iprot.readString();
            struct.partitionVals.add(_elem1112);
          }
        }
        struct.setPartitionValsIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

