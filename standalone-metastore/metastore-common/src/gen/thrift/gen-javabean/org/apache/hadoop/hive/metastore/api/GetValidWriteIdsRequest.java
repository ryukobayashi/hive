/**
 * Autogenerated by Thrift Compiler (0.16.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.metastore.api;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.16.0)")
@org.apache.hadoop.classification.InterfaceAudience.Public @org.apache.hadoop.classification.InterfaceStability.Stable public class GetValidWriteIdsRequest implements org.apache.thrift.TBase<GetValidWriteIdsRequest, GetValidWriteIdsRequest._Fields>, java.io.Serializable, Cloneable, Comparable<GetValidWriteIdsRequest> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("GetValidWriteIdsRequest");

  private static final org.apache.thrift.protocol.TField FULL_TABLE_NAMES_FIELD_DESC = new org.apache.thrift.protocol.TField("fullTableNames", org.apache.thrift.protocol.TType.LIST, (short)1);
  private static final org.apache.thrift.protocol.TField VALID_TXN_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("validTxnList", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField WRITE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("writeId", org.apache.thrift.protocol.TType.I64, (short)3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new GetValidWriteIdsRequestStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new GetValidWriteIdsRequestTupleSchemeFactory();

  private @org.apache.thrift.annotation.Nullable java.util.List<java.lang.String> fullTableNames; // required
  private @org.apache.thrift.annotation.Nullable java.lang.String validTxnList; // optional
  private long writeId; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    FULL_TABLE_NAMES((short)1, "fullTableNames"),
    VALID_TXN_LIST((short)2, "validTxnList"),
    WRITE_ID((short)3, "writeId");

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
        case 1: // FULL_TABLE_NAMES
          return FULL_TABLE_NAMES;
        case 2: // VALID_TXN_LIST
          return VALID_TXN_LIST;
        case 3: // WRITE_ID
          return WRITE_ID;
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
  private static final int __WRITEID_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.VALID_TXN_LIST,_Fields.WRITE_ID};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.FULL_TABLE_NAMES, new org.apache.thrift.meta_data.FieldMetaData("fullTableNames", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    tmpMap.put(_Fields.VALID_TXN_LIST, new org.apache.thrift.meta_data.FieldMetaData("validTxnList", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.WRITE_ID, new org.apache.thrift.meta_data.FieldMetaData("writeId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(GetValidWriteIdsRequest.class, metaDataMap);
  }

  public GetValidWriteIdsRequest() {
  }

  public GetValidWriteIdsRequest(
    java.util.List<java.lang.String> fullTableNames)
  {
    this();
    this.fullTableNames = fullTableNames;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public GetValidWriteIdsRequest(GetValidWriteIdsRequest other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetFullTableNames()) {
      java.util.List<java.lang.String> __this__fullTableNames = new java.util.ArrayList<java.lang.String>(other.fullTableNames);
      this.fullTableNames = __this__fullTableNames;
    }
    if (other.isSetValidTxnList()) {
      this.validTxnList = other.validTxnList;
    }
    this.writeId = other.writeId;
  }

  public GetValidWriteIdsRequest deepCopy() {
    return new GetValidWriteIdsRequest(this);
  }

  @Override
  public void clear() {
    this.fullTableNames = null;
    this.validTxnList = null;
    setWriteIdIsSet(false);
    this.writeId = 0;
  }

  public int getFullTableNamesSize() {
    return (this.fullTableNames == null) ? 0 : this.fullTableNames.size();
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.Iterator<java.lang.String> getFullTableNamesIterator() {
    return (this.fullTableNames == null) ? null : this.fullTableNames.iterator();
  }

  public void addToFullTableNames(java.lang.String elem) {
    if (this.fullTableNames == null) {
      this.fullTableNames = new java.util.ArrayList<java.lang.String>();
    }
    this.fullTableNames.add(elem);
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.List<java.lang.String> getFullTableNames() {
    return this.fullTableNames;
  }

  public void setFullTableNames(@org.apache.thrift.annotation.Nullable java.util.List<java.lang.String> fullTableNames) {
    this.fullTableNames = fullTableNames;
  }

  public void unsetFullTableNames() {
    this.fullTableNames = null;
  }

  /** Returns true if field fullTableNames is set (has been assigned a value) and false otherwise */
  public boolean isSetFullTableNames() {
    return this.fullTableNames != null;
  }

  public void setFullTableNamesIsSet(boolean value) {
    if (!value) {
      this.fullTableNames = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getValidTxnList() {
    return this.validTxnList;
  }

  public void setValidTxnList(@org.apache.thrift.annotation.Nullable java.lang.String validTxnList) {
    this.validTxnList = validTxnList;
  }

  public void unsetValidTxnList() {
    this.validTxnList = null;
  }

  /** Returns true if field validTxnList is set (has been assigned a value) and false otherwise */
  public boolean isSetValidTxnList() {
    return this.validTxnList != null;
  }

  public void setValidTxnListIsSet(boolean value) {
    if (!value) {
      this.validTxnList = null;
    }
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

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case FULL_TABLE_NAMES:
      if (value == null) {
        unsetFullTableNames();
      } else {
        setFullTableNames((java.util.List<java.lang.String>)value);
      }
      break;

    case VALID_TXN_LIST:
      if (value == null) {
        unsetValidTxnList();
      } else {
        setValidTxnList((java.lang.String)value);
      }
      break;

    case WRITE_ID:
      if (value == null) {
        unsetWriteId();
      } else {
        setWriteId((java.lang.Long)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case FULL_TABLE_NAMES:
      return getFullTableNames();

    case VALID_TXN_LIST:
      return getValidTxnList();

    case WRITE_ID:
      return getWriteId();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case FULL_TABLE_NAMES:
      return isSetFullTableNames();
    case VALID_TXN_LIST:
      return isSetValidTxnList();
    case WRITE_ID:
      return isSetWriteId();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that instanceof GetValidWriteIdsRequest)
      return this.equals((GetValidWriteIdsRequest)that);
    return false;
  }

  public boolean equals(GetValidWriteIdsRequest that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_fullTableNames = true && this.isSetFullTableNames();
    boolean that_present_fullTableNames = true && that.isSetFullTableNames();
    if (this_present_fullTableNames || that_present_fullTableNames) {
      if (!(this_present_fullTableNames && that_present_fullTableNames))
        return false;
      if (!this.fullTableNames.equals(that.fullTableNames))
        return false;
    }

    boolean this_present_validTxnList = true && this.isSetValidTxnList();
    boolean that_present_validTxnList = true && that.isSetValidTxnList();
    if (this_present_validTxnList || that_present_validTxnList) {
      if (!(this_present_validTxnList && that_present_validTxnList))
        return false;
      if (!this.validTxnList.equals(that.validTxnList))
        return false;
    }

    boolean this_present_writeId = true && this.isSetWriteId();
    boolean that_present_writeId = true && that.isSetWriteId();
    if (this_present_writeId || that_present_writeId) {
      if (!(this_present_writeId && that_present_writeId))
        return false;
      if (this.writeId != that.writeId)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetFullTableNames()) ? 131071 : 524287);
    if (isSetFullTableNames())
      hashCode = hashCode * 8191 + fullTableNames.hashCode();

    hashCode = hashCode * 8191 + ((isSetValidTxnList()) ? 131071 : 524287);
    if (isSetValidTxnList())
      hashCode = hashCode * 8191 + validTxnList.hashCode();

    hashCode = hashCode * 8191 + ((isSetWriteId()) ? 131071 : 524287);
    if (isSetWriteId())
      hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(writeId);

    return hashCode;
  }

  @Override
  public int compareTo(GetValidWriteIdsRequest other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.compare(isSetFullTableNames(), other.isSetFullTableNames());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFullTableNames()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.fullTableNames, other.fullTableNames);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetValidTxnList(), other.isSetValidTxnList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetValidTxnList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.validTxnList, other.validTxnList);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("GetValidWriteIdsRequest(");
    boolean first = true;

    sb.append("fullTableNames:");
    if (this.fullTableNames == null) {
      sb.append("null");
    } else {
      sb.append(this.fullTableNames);
    }
    first = false;
    if (isSetValidTxnList()) {
      if (!first) sb.append(", ");
      sb.append("validTxnList:");
      if (this.validTxnList == null) {
        sb.append("null");
      } else {
        sb.append(this.validTxnList);
      }
      first = false;
    }
    if (isSetWriteId()) {
      if (!first) sb.append(", ");
      sb.append("writeId:");
      sb.append(this.writeId);
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!isSetFullTableNames()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'fullTableNames' is unset! Struct:" + toString());
    }

    // check for sub-struct validity
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

  private static class GetValidWriteIdsRequestStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public GetValidWriteIdsRequestStandardScheme getScheme() {
      return new GetValidWriteIdsRequestStandardScheme();
    }
  }

  private static class GetValidWriteIdsRequestStandardScheme extends org.apache.thrift.scheme.StandardScheme<GetValidWriteIdsRequest> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, GetValidWriteIdsRequest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // FULL_TABLE_NAMES
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list854 = iprot.readListBegin();
                struct.fullTableNames = new java.util.ArrayList<java.lang.String>(_list854.size);
                @org.apache.thrift.annotation.Nullable java.lang.String _elem855;
                for (int _i856 = 0; _i856 < _list854.size; ++_i856)
                {
                  _elem855 = iprot.readString();
                  struct.fullTableNames.add(_elem855);
                }
                iprot.readListEnd();
              }
              struct.setFullTableNamesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // VALID_TXN_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.validTxnList = iprot.readString();
              struct.setValidTxnListIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // WRITE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.writeId = iprot.readI64();
              struct.setWriteIdIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, GetValidWriteIdsRequest struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.fullTableNames != null) {
        oprot.writeFieldBegin(FULL_TABLE_NAMES_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.fullTableNames.size()));
          for (java.lang.String _iter857 : struct.fullTableNames)
          {
            oprot.writeString(_iter857);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.validTxnList != null) {
        if (struct.isSetValidTxnList()) {
          oprot.writeFieldBegin(VALID_TXN_LIST_FIELD_DESC);
          oprot.writeString(struct.validTxnList);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetWriteId()) {
        oprot.writeFieldBegin(WRITE_ID_FIELD_DESC);
        oprot.writeI64(struct.writeId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class GetValidWriteIdsRequestTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public GetValidWriteIdsRequestTupleScheme getScheme() {
      return new GetValidWriteIdsRequestTupleScheme();
    }
  }

  private static class GetValidWriteIdsRequestTupleScheme extends org.apache.thrift.scheme.TupleScheme<GetValidWriteIdsRequest> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, GetValidWriteIdsRequest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      {
        oprot.writeI32(struct.fullTableNames.size());
        for (java.lang.String _iter858 : struct.fullTableNames)
        {
          oprot.writeString(_iter858);
        }
      }
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetValidTxnList()) {
        optionals.set(0);
      }
      if (struct.isSetWriteId()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetValidTxnList()) {
        oprot.writeString(struct.validTxnList);
      }
      if (struct.isSetWriteId()) {
        oprot.writeI64(struct.writeId);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, GetValidWriteIdsRequest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      {
        org.apache.thrift.protocol.TList _list859 = iprot.readListBegin(org.apache.thrift.protocol.TType.STRING);
        struct.fullTableNames = new java.util.ArrayList<java.lang.String>(_list859.size);
        @org.apache.thrift.annotation.Nullable java.lang.String _elem860;
        for (int _i861 = 0; _i861 < _list859.size; ++_i861)
        {
          _elem860 = iprot.readString();
          struct.fullTableNames.add(_elem860);
        }
      }
      struct.setFullTableNamesIsSet(true);
      java.util.BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.validTxnList = iprot.readString();
        struct.setValidTxnListIsSet(true);
      }
      if (incoming.get(1)) {
        struct.writeId = iprot.readI64();
        struct.setWriteIdIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

