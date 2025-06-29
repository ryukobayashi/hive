/**
 * Autogenerated by Thrift Compiler (0.16.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.metastore.api;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.16.0)")
@org.apache.hadoop.classification.InterfaceAudience.Public @org.apache.hadoop.classification.InterfaceStability.Stable public class GetPartitionsByNamesResult implements org.apache.thrift.TBase<GetPartitionsByNamesResult, GetPartitionsByNamesResult._Fields>, java.io.Serializable, Cloneable, Comparable<GetPartitionsByNamesResult> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("GetPartitionsByNamesResult");

  private static final org.apache.thrift.protocol.TField PARTITIONS_FIELD_DESC = new org.apache.thrift.protocol.TField("partitions", org.apache.thrift.protocol.TType.LIST, (short)1);
  private static final org.apache.thrift.protocol.TField DICTIONARY_FIELD_DESC = new org.apache.thrift.protocol.TField("dictionary", org.apache.thrift.protocol.TType.STRUCT, (short)2);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new GetPartitionsByNamesResultStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new GetPartitionsByNamesResultTupleSchemeFactory();

  private @org.apache.thrift.annotation.Nullable java.util.List<Partition> partitions; // required
  private @org.apache.thrift.annotation.Nullable ObjectDictionary dictionary; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PARTITIONS((short)1, "partitions"),
    DICTIONARY((short)2, "dictionary");

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
        case 1: // PARTITIONS
          return PARTITIONS;
        case 2: // DICTIONARY
          return DICTIONARY;
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
  private static final _Fields optionals[] = {_Fields.DICTIONARY};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PARTITIONS, new org.apache.thrift.meta_data.FieldMetaData("partitions", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Partition.class))));
    tmpMap.put(_Fields.DICTIONARY, new org.apache.thrift.meta_data.FieldMetaData("dictionary", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ObjectDictionary.class)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(GetPartitionsByNamesResult.class, metaDataMap);
  }

  public GetPartitionsByNamesResult() {
  }

  public GetPartitionsByNamesResult(
    java.util.List<Partition> partitions)
  {
    this();
    this.partitions = partitions;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public GetPartitionsByNamesResult(GetPartitionsByNamesResult other) {
    if (other.isSetPartitions()) {
      java.util.List<Partition> __this__partitions = new java.util.ArrayList<Partition>(other.partitions.size());
      for (Partition other_element : other.partitions) {
        __this__partitions.add(new Partition(other_element));
      }
      this.partitions = __this__partitions;
    }
    if (other.isSetDictionary()) {
      this.dictionary = new ObjectDictionary(other.dictionary);
    }
  }

  public GetPartitionsByNamesResult deepCopy() {
    return new GetPartitionsByNamesResult(this);
  }

  @Override
  public void clear() {
    this.partitions = null;
    this.dictionary = null;
  }

  public int getPartitionsSize() {
    return (this.partitions == null) ? 0 : this.partitions.size();
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.Iterator<Partition> getPartitionsIterator() {
    return (this.partitions == null) ? null : this.partitions.iterator();
  }

  public void addToPartitions(Partition elem) {
    if (this.partitions == null) {
      this.partitions = new java.util.ArrayList<Partition>();
    }
    this.partitions.add(elem);
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.List<Partition> getPartitions() {
    return this.partitions;
  }

  public void setPartitions(@org.apache.thrift.annotation.Nullable java.util.List<Partition> partitions) {
    this.partitions = partitions;
  }

  public void unsetPartitions() {
    this.partitions = null;
  }

  /** Returns true if field partitions is set (has been assigned a value) and false otherwise */
  public boolean isSetPartitions() {
    return this.partitions != null;
  }

  public void setPartitionsIsSet(boolean value) {
    if (!value) {
      this.partitions = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public ObjectDictionary getDictionary() {
    return this.dictionary;
  }

  public void setDictionary(@org.apache.thrift.annotation.Nullable ObjectDictionary dictionary) {
    this.dictionary = dictionary;
  }

  public void unsetDictionary() {
    this.dictionary = null;
  }

  /** Returns true if field dictionary is set (has been assigned a value) and false otherwise */
  public boolean isSetDictionary() {
    return this.dictionary != null;
  }

  public void setDictionaryIsSet(boolean value) {
    if (!value) {
      this.dictionary = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case PARTITIONS:
      if (value == null) {
        unsetPartitions();
      } else {
        setPartitions((java.util.List<Partition>)value);
      }
      break;

    case DICTIONARY:
      if (value == null) {
        unsetDictionary();
      } else {
        setDictionary((ObjectDictionary)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case PARTITIONS:
      return getPartitions();

    case DICTIONARY:
      return getDictionary();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case PARTITIONS:
      return isSetPartitions();
    case DICTIONARY:
      return isSetDictionary();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that instanceof GetPartitionsByNamesResult)
      return this.equals((GetPartitionsByNamesResult)that);
    return false;
  }

  public boolean equals(GetPartitionsByNamesResult that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_partitions = true && this.isSetPartitions();
    boolean that_present_partitions = true && that.isSetPartitions();
    if (this_present_partitions || that_present_partitions) {
      if (!(this_present_partitions && that_present_partitions))
        return false;
      if (!this.partitions.equals(that.partitions))
        return false;
    }

    boolean this_present_dictionary = true && this.isSetDictionary();
    boolean that_present_dictionary = true && that.isSetDictionary();
    if (this_present_dictionary || that_present_dictionary) {
      if (!(this_present_dictionary && that_present_dictionary))
        return false;
      if (!this.dictionary.equals(that.dictionary))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetPartitions()) ? 131071 : 524287);
    if (isSetPartitions())
      hashCode = hashCode * 8191 + partitions.hashCode();

    hashCode = hashCode * 8191 + ((isSetDictionary()) ? 131071 : 524287);
    if (isSetDictionary())
      hashCode = hashCode * 8191 + dictionary.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(GetPartitionsByNamesResult other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.compare(isSetPartitions(), other.isSetPartitions());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPartitions()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.partitions, other.partitions);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetDictionary(), other.isSetDictionary());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDictionary()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.dictionary, other.dictionary);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("GetPartitionsByNamesResult(");
    boolean first = true;

    sb.append("partitions:");
    if (this.partitions == null) {
      sb.append("null");
    } else {
      sb.append(this.partitions);
    }
    first = false;
    if (isSetDictionary()) {
      if (!first) sb.append(", ");
      sb.append("dictionary:");
      if (this.dictionary == null) {
        sb.append("null");
      } else {
        sb.append(this.dictionary);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!isSetPartitions()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'partitions' is unset! Struct:" + toString());
    }

    // check for sub-struct validity
    if (dictionary != null) {
      dictionary.validate();
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class GetPartitionsByNamesResultStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public GetPartitionsByNamesResultStandardScheme getScheme() {
      return new GetPartitionsByNamesResultStandardScheme();
    }
  }

  private static class GetPartitionsByNamesResultStandardScheme extends org.apache.thrift.scheme.StandardScheme<GetPartitionsByNamesResult> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, GetPartitionsByNamesResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PARTITIONS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list764 = iprot.readListBegin();
                struct.partitions = new java.util.ArrayList<Partition>(_list764.size);
                @org.apache.thrift.annotation.Nullable Partition _elem765;
                for (int _i766 = 0; _i766 < _list764.size; ++_i766)
                {
                  _elem765 = new Partition();
                  _elem765.read(iprot);
                  struct.partitions.add(_elem765);
                }
                iprot.readListEnd();
              }
              struct.setPartitionsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // DICTIONARY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.dictionary = new ObjectDictionary();
              struct.dictionary.read(iprot);
              struct.setDictionaryIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, GetPartitionsByNamesResult struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.partitions != null) {
        oprot.writeFieldBegin(PARTITIONS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.partitions.size()));
          for (Partition _iter767 : struct.partitions)
          {
            _iter767.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.dictionary != null) {
        if (struct.isSetDictionary()) {
          oprot.writeFieldBegin(DICTIONARY_FIELD_DESC);
          struct.dictionary.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class GetPartitionsByNamesResultTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public GetPartitionsByNamesResultTupleScheme getScheme() {
      return new GetPartitionsByNamesResultTupleScheme();
    }
  }

  private static class GetPartitionsByNamesResultTupleScheme extends org.apache.thrift.scheme.TupleScheme<GetPartitionsByNamesResult> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, GetPartitionsByNamesResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      {
        oprot.writeI32(struct.partitions.size());
        for (Partition _iter768 : struct.partitions)
        {
          _iter768.write(oprot);
        }
      }
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetDictionary()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetDictionary()) {
        struct.dictionary.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, GetPartitionsByNamesResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      {
        org.apache.thrift.protocol.TList _list769 = iprot.readListBegin(org.apache.thrift.protocol.TType.STRUCT);
        struct.partitions = new java.util.ArrayList<Partition>(_list769.size);
        @org.apache.thrift.annotation.Nullable Partition _elem770;
        for (int _i771 = 0; _i771 < _list769.size; ++_i771)
        {
          _elem770 = new Partition();
          _elem770.read(iprot);
          struct.partitions.add(_elem770);
        }
      }
      struct.setPartitionsIsSet(true);
      java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.dictionary = new ObjectDictionary();
        struct.dictionary.read(iprot);
        struct.setDictionaryIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

