<?php
namespace metastore;

/**
 * Autogenerated by Thrift Compiler (0.14.1)
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

class DropDatabaseRequest
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'name',
            'isRequired' => true,
            'type' => TType::STRING,
        ),
        2 => array(
            'var' => 'catalogName',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        3 => array(
            'var' => 'ignoreUnknownDb',
            'isRequired' => true,
            'type' => TType::BOOL,
        ),
        4 => array(
            'var' => 'deleteData',
            'isRequired' => true,
            'type' => TType::BOOL,
        ),
        5 => array(
            'var' => 'cascade',
            'isRequired' => true,
            'type' => TType::BOOL,
        ),
        6 => array(
            'var' => 'softDelete',
            'isRequired' => false,
            'type' => TType::BOOL,
        ),
        7 => array(
            'var' => 'txnId',
            'isRequired' => false,
            'type' => TType::I64,
        ),
        8 => array(
            'var' => 'deleteManagedDir',
            'isRequired' => false,
            'type' => TType::BOOL,
        ),
    );

    /**
     * @var string
     */
    public $name = null;
    /**
     * @var string
     */
    public $catalogName = null;
    /**
     * @var bool
     */
    public $ignoreUnknownDb = null;
    /**
     * @var bool
     */
    public $deleteData = null;
    /**
     * @var bool
     */
    public $cascade = null;
    /**
     * @var bool
     */
    public $softDelete = false;
    /**
     * @var int
     */
    public $txnId = 0;
    /**
     * @var bool
     */
    public $deleteManagedDir = true;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['name'])) {
                $this->name = $vals['name'];
            }
            if (isset($vals['catalogName'])) {
                $this->catalogName = $vals['catalogName'];
            }
            if (isset($vals['ignoreUnknownDb'])) {
                $this->ignoreUnknownDb = $vals['ignoreUnknownDb'];
            }
            if (isset($vals['deleteData'])) {
                $this->deleteData = $vals['deleteData'];
            }
            if (isset($vals['cascade'])) {
                $this->cascade = $vals['cascade'];
            }
            if (isset($vals['softDelete'])) {
                $this->softDelete = $vals['softDelete'];
            }
            if (isset($vals['txnId'])) {
                $this->txnId = $vals['txnId'];
            }
            if (isset($vals['deleteManagedDir'])) {
                $this->deleteManagedDir = $vals['deleteManagedDir'];
            }
        }
    }

    public function getName()
    {
        return 'DropDatabaseRequest';
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
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->name);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 2:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->catalogName);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 3:
                    if ($ftype == TType::BOOL) {
                        $xfer += $input->readBool($this->ignoreUnknownDb);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 4:
                    if ($ftype == TType::BOOL) {
                        $xfer += $input->readBool($this->deleteData);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 5:
                    if ($ftype == TType::BOOL) {
                        $xfer += $input->readBool($this->cascade);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 6:
                    if ($ftype == TType::BOOL) {
                        $xfer += $input->readBool($this->softDelete);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 7:
                    if ($ftype == TType::I64) {
                        $xfer += $input->readI64($this->txnId);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 8:
                    if ($ftype == TType::BOOL) {
                        $xfer += $input->readBool($this->deleteManagedDir);
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
        $xfer += $output->writeStructBegin('DropDatabaseRequest');
        if ($this->name !== null) {
            $xfer += $output->writeFieldBegin('name', TType::STRING, 1);
            $xfer += $output->writeString($this->name);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->catalogName !== null) {
            $xfer += $output->writeFieldBegin('catalogName', TType::STRING, 2);
            $xfer += $output->writeString($this->catalogName);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->ignoreUnknownDb !== null) {
            $xfer += $output->writeFieldBegin('ignoreUnknownDb', TType::BOOL, 3);
            $xfer += $output->writeBool($this->ignoreUnknownDb);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->deleteData !== null) {
            $xfer += $output->writeFieldBegin('deleteData', TType::BOOL, 4);
            $xfer += $output->writeBool($this->deleteData);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->cascade !== null) {
            $xfer += $output->writeFieldBegin('cascade', TType::BOOL, 5);
            $xfer += $output->writeBool($this->cascade);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->softDelete !== null) {
            $xfer += $output->writeFieldBegin('softDelete', TType::BOOL, 6);
            $xfer += $output->writeBool($this->softDelete);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->txnId !== null) {
            $xfer += $output->writeFieldBegin('txnId', TType::I64, 7);
            $xfer += $output->writeI64($this->txnId);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->deleteManagedDir !== null) {
            $xfer += $output->writeFieldBegin('deleteManagedDir', TType::BOOL, 8);
            $xfer += $output->writeBool($this->deleteManagedDir);
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}