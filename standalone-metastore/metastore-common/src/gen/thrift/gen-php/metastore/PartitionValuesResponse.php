<?php
namespace metastore;

/**
 * Autogenerated by Thrift Compiler (0.16.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
use Thrift\Base\TBase;
use Thrift\Type\TType;
use Thrift\Type\TMessageType;
use Thrift\Exception\TException;
use Thrift\Exception\TProtocolException;
use Thrift\Protocol\TProtocol;
use Thrift\Protocol\TBinaryProtocolAccelerated;
use Thrift\Exception\TApplicationException;

class PartitionValuesResponse
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'partitionValues',
            'isRequired' => true,
            'type' => TType::LST,
            'etype' => TType::STRUCT,
            'elem' => array(
                'type' => TType::STRUCT,
                'class' => '\metastore\PartitionValuesRow',
                ),
        ),
    );

    /**
     * @var \metastore\PartitionValuesRow[]
     */
    public $partitionValues = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['partitionValues'])) {
                $this->partitionValues = $vals['partitionValues'];
            }
        }
    }

    public function getName()
    {
        return 'PartitionValuesResponse';
    }


    public function read($input)
    {
        $xfer = 0;
        $fname = null;
        $ftype = 0;
        $fid = 0;
        $xfer += $input->readStructBegin($fname);
        while (true) {
            $xfer += $input->readFieldBegin($fname, $ftype, $fid);
            if ($ftype == TType::STOP) {
                break;
            }
            switch ($fid) {
                case 1:
                    if ($ftype == TType::LST) {
                        $this->partitionValues = array();
                        $_size653 = 0;
                        $_etype656 = 0;
                        $xfer += $input->readListBegin($_etype656, $_size653);
                        for ($_i657 = 0; $_i657 < $_size653; ++$_i657) {
                            $elem658 = null;
                            $elem658 = new \metastore\PartitionValuesRow();
                            $xfer += $elem658->read($input);
                            $this->partitionValues []= $elem658;
                        }
                        $xfer += $input->readListEnd();
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                default:
                    $xfer += $input->skip($ftype);
                    break;
            }
            $xfer += $input->readFieldEnd();
        }
        $xfer += $input->readStructEnd();
        return $xfer;
    }

    public function write($output)
    {
        $xfer = 0;
        $xfer += $output->writeStructBegin('PartitionValuesResponse');
        if ($this->partitionValues !== null) {
            if (!is_array($this->partitionValues)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('partitionValues', TType::LST, 1);
            $output->writeListBegin(TType::STRUCT, count($this->partitionValues));
            foreach ($this->partitionValues as $iter659) {
                $xfer += $iter659->write($output);
            }
            $output->writeListEnd();
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
