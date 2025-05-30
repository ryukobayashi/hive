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

class FireEventRequestData
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'insertData',
            'isRequired' => false,
            'type' => TType::STRUCT,
            'class' => '\metastore\InsertEventRequestData',
        ),
        2 => array(
            'var' => 'insertDatas',
            'isRequired' => false,
            'type' => TType::LST,
            'etype' => TType::STRUCT,
            'elem' => array(
                'type' => TType::STRUCT,
                'class' => '\metastore\InsertEventRequestData',
                ),
        ),
        3 => array(
            'var' => 'refreshEvent',
            'isRequired' => false,
            'type' => TType::BOOL,
        ),
    );

    /**
     * @var \metastore\InsertEventRequestData
     */
    public $insertData = null;
    /**
     * @var \metastore\InsertEventRequestData[]
     */
    public $insertDatas = null;
    /**
     * @var bool
     */
    public $refreshEvent = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['insertData'])) {
                $this->insertData = $vals['insertData'];
            }
            if (isset($vals['insertDatas'])) {
                $this->insertDatas = $vals['insertDatas'];
            }
            if (isset($vals['refreshEvent'])) {
                $this->refreshEvent = $vals['refreshEvent'];
            }
        }
    }

    public function getName()
    {
        return 'FireEventRequestData';
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
                    if ($ftype == TType::STRUCT) {
                        $this->insertData = new \metastore\InsertEventRequestData();
                        $xfer += $this->insertData->read($input);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 2:
                    if ($ftype == TType::LST) {
                        $this->insertDatas = array();
                        $_size932 = 0;
                        $_etype935 = 0;
                        $xfer += $input->readListBegin($_etype935, $_size932);
                        for ($_i936 = 0; $_i936 < $_size932; ++$_i936) {
                            $elem937 = null;
                            $elem937 = new \metastore\InsertEventRequestData();
                            $xfer += $elem937->read($input);
                            $this->insertDatas []= $elem937;
                        }
                        $xfer += $input->readListEnd();
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 3:
                    if ($ftype == TType::BOOL) {
                        $xfer += $input->readBool($this->refreshEvent);
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
        $xfer += $output->writeStructBegin('FireEventRequestData');
        if ($this->insertData !== null) {
            if (!is_object($this->insertData)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('insertData', TType::STRUCT, 1);
            $xfer += $this->insertData->write($output);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->insertDatas !== null) {
            if (!is_array($this->insertDatas)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('insertDatas', TType::LST, 2);
            $output->writeListBegin(TType::STRUCT, count($this->insertDatas));
            foreach ($this->insertDatas as $iter938) {
                $xfer += $iter938->write($output);
            }
            $output->writeListEnd();
            $xfer += $output->writeFieldEnd();
        }
        if ($this->refreshEvent !== null) {
            $xfer += $output->writeFieldBegin('refreshEvent', TType::BOOL, 3);
            $xfer += $output->writeBool($this->refreshEvent);
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
