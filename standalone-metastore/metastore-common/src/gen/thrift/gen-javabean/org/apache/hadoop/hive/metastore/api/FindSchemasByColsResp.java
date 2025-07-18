/**
 * Autogenerated by Thrift Compiler (0.16.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.metastore.api;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.16.0)")
@org.apache.hadoop.classification.InterfaceAudience.Public @org.apache.hadoop.classification.InterfaceStability.Stable public class FindSchemasByColsResp implements org.apache.thrift.TBase<FindSchemasByColsResp, FindSchemasByColsResp._Fields>, java.io.Serializable, Cloneable, Comparable<FindSchemasByColsResp> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("FindSchemasByColsResp");

  private static final org.apache.thrift.protocol.TField SCHEMA_VERSIONS_FIELD_DESC = new org.apache.thrift.protocol.TField("schemaVersions", org.apache.thrift.protocol.TType.LIST, (short)1);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new FindSchemasByColsRespStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new FindSchemasByColsRespTupleSchemeFactory();

  private @org.apache.thrift.annotation.Nullable java.util.List<SchemaVersionDescriptor> schemaVersions; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SCHEMA_VERSIONS((short)1, "schemaVersions");

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
        case 1: // SCHEMA_VERSIONS
          return SCHEMA_VERSIONS;
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
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SCHEMA_VERSIONS, new org.apache.thrift.meta_data.FieldMetaData("schemaVersions", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, SchemaVersionDescriptor.class))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(FindSchemasByColsResp.class, metaDataMap);
  }

  public FindSchemasByColsResp() {
  }

  public FindSchemasByColsResp(
    java.util.List<SchemaVersionDescriptor> schemaVersions)
  {
    this();
    this.schemaVersions = schemaVersions;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public FindSchemasByColsResp(FindSchemasByColsResp other) {
    if (other.isSetSchemaVersions()) {
      java.util.List<SchemaVersionDescriptor> __this__schemaVersions = new java.util.ArrayList<SchemaVersionDescriptor>(other.schemaVersions.size());
      for (SchemaVersionDescriptor other_element : other.schemaVersions) {
        __this__schemaVersions.add(new SchemaVersionDescriptor(other_element));
      }
      this.schemaVersions = __this__schemaVersions;
    }
  }

  public FindSchemasByColsResp deepCopy() {
    return new FindSchemasByColsResp(this);
  }

  @Override
  public void clear() {
    this.schemaVersions = null;
  }

  public int getSchemaVersionsSize() {
    return (this.schemaVersions == null) ? 0 : this.schemaVersions.size();
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.Iterator<SchemaVersionDescriptor> getSchemaVersionsIterator() {
    return (this.schemaVersions == null) ? null : this.schemaVersions.iterator();
  }

  public void addToSchemaVersions(SchemaVersionDescriptor elem) {
    if (this.schemaVersions == null) {
      this.schemaVersions = new java.util.ArrayList<SchemaVersionDescriptor>();
    }
    this.schemaVersions.add(elem);
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.List<SchemaVersionDescriptor> getSchemaVersions() {
    return this.schemaVersions;
  }

  public void setSchemaVersions(@org.apache.thrift.annotation.Nullable java.util.List<SchemaVersionDescriptor> schemaVersions) {
    this.schemaVersions = schemaVersions;
  }

  public void unsetSchemaVersions() {
    this.schemaVersions = null;
  }

  /** Returns true if field schemaVersions is set (has been assigned a value) and false otherwise */
  public boolean isSetSchemaVersions() {
    return this.schemaVersions != null;
  }

  public void setSchemaVersionsIsSet(boolean value) {
    if (!value) {
      this.schemaVersions = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case SCHEMA_VERSIONS:
      if (value == null) {
        unsetSchemaVersions();
      } else {
        setSchemaVersions((java.util.List<SchemaVersionDescriptor>)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case SCHEMA_VERSIONS:
      return getSchemaVersions();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case SCHEMA_VERSIONS:
      return isSetSchemaVersions();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that instanceof FindSchemasByColsResp)
      return this.equals((FindSchemasByColsResp)that);
    return false;
  }

  public boolean equals(FindSchemasByColsResp that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_schemaVersions = true && this.isSetSchemaVersions();
    boolean that_present_schemaVersions = true && that.isSetSchemaVersions();
    if (this_present_schemaVersions || that_present_schemaVersions) {
      if (!(this_present_schemaVersions && that_present_schemaVersions))
        return false;
      if (!this.schemaVersions.equals(that.schemaVersions))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetSchemaVersions()) ? 131071 : 524287);
    if (isSetSchemaVersions())
      hashCode = hashCode * 8191 + schemaVersions.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(FindSchemasByColsResp other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.compare(isSetSchemaVersions(), other.isSetSchemaVersions());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSchemaVersions()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.schemaVersions, other.schemaVersions);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("FindSchemasByColsResp(");
    boolean first = true;

    sb.append("schemaVersions:");
    if (this.schemaVersions == null) {
      sb.append("null");
    } else {
      sb.append(this.schemaVersions);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class FindSchemasByColsRespStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public FindSchemasByColsRespStandardScheme getScheme() {
      return new FindSchemasByColsRespStandardScheme();
    }
  }

  private static class FindSchemasByColsRespStandardScheme extends org.apache.thrift.scheme.StandardScheme<FindSchemasByColsResp> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, FindSchemasByColsResp struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // SCHEMA_VERSIONS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list1358 = iprot.readListBegin();
                struct.schemaVersions = new java.util.ArrayList<SchemaVersionDescriptor>(_list1358.size);
                @org.apache.thrift.annotation.Nullable SchemaVersionDescriptor _elem1359;
                for (int _i1360 = 0; _i1360 < _list1358.size; ++_i1360)
                {
                  _elem1359 = new SchemaVersionDescriptor();
                  _elem1359.read(iprot);
                  struct.schemaVersions.add(_elem1359);
                }
                iprot.readListEnd();
              }
              struct.setSchemaVersionsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, FindSchemasByColsResp struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.schemaVersions != null) {
        oprot.writeFieldBegin(SCHEMA_VERSIONS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.schemaVersions.size()));
          for (SchemaVersionDescriptor _iter1361 : struct.schemaVersions)
          {
            _iter1361.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class FindSchemasByColsRespTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public FindSchemasByColsRespTupleScheme getScheme() {
      return new FindSchemasByColsRespTupleScheme();
    }
  }

  private static class FindSchemasByColsRespTupleScheme extends org.apache.thrift.scheme.TupleScheme<FindSchemasByColsResp> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, FindSchemasByColsResp struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetSchemaVersions()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetSchemaVersions()) {
        {
          oprot.writeI32(struct.schemaVersions.size());
          for (SchemaVersionDescriptor _iter1362 : struct.schemaVersions)
          {
            _iter1362.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, FindSchemasByColsResp struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list1363 = iprot.readListBegin(org.apache.thrift.protocol.TType.STRUCT);
          struct.schemaVersions = new java.util.ArrayList<SchemaVersionDescriptor>(_list1363.size);
          @org.apache.thrift.annotation.Nullable SchemaVersionDescriptor _elem1364;
          for (int _i1365 = 0; _i1365 < _list1363.size; ++_i1365)
          {
            _elem1364 = new SchemaVersionDescriptor();
            _elem1364.read(iprot);
            struct.schemaVersions.add(_elem1364);
          }
        }
        struct.setSchemaVersionsIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

