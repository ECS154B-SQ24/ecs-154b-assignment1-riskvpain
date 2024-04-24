// This file contains ALU control logic.

package dinocpu.components

import chisel3._
import chisel3.util._

/**
 * The ALU control unit
 *
 * Input:  aluop        Specifying the type of instruction using ALU
 *                          . 0 for none of the below
 *                          . 1 for 64-bit R-type
 *                          . 2 for 64-bit I-type
 *                          . 3 for 32-bit R-type
 *                          . 4 for 32-bit I-type
 *                          . 5 for non-arithmetic instruction types that uses ALU (auipc/jal/jarl/Load/Store)
 * Input:  funct7       The most significant bits of the instruction.
 * Input:  funct3       The middle three bits of the instruction (12-14).
 *
 * Output: operation    What we want the ALU to do.
 *
 * For more information, see Section 4.4 and A.5 of Patterson and Hennessy.
 * This is loosely based on figure 4.12
 */
class ALUControl extends Module {
  val io = IO(new Bundle {
    val aluop     = Input(UInt(3.W))
    val funct7    = Input(UInt(7.W))
    val funct3    = Input(UInt(3.W))

    val operation = Output(UInt(5.W))
  })

  io.operation := "b11111".U // Invalid

  //Your code goes here
   when (io.funct7 === 0.U) { // when funct7 = 0.U
    when (io.aluop  === 1.U) { // condition for when aluop is 0
        when (io.funct3 === 0.U) { // individual conditions
          io.operation := 1.U
        } .elsewhen (io.funct3 === 1.U) {
           io.operation := 18.U
        } .elsewhen (io.funct3 === 2.U) {
           io.operation := 22.U
        } .elsewhen (io.funct3 === 3.U) {
           io.operation := 23.U
        } .elsewhen (io.funct3 === 4.U) {
          io.operation := 15.U
        } .elsewhen (io.funct3 === 5.U) {
          io.operation := 20.U
        } .elsewhen (io.funct3 === 6.U) {
          io.operation := 14.U
        } .otherwise {
          io.operation := 13.U
        }
    } .otherwise { // aluop is 1
        when (io.funct3 === 0.U) { // individual conditions
          io.operation := 0.U
        } .elsewhen (io.funct3 === 1.U) {
          io.operation := 19.U
        } otherwise {
          io.operation := 21.U
        }
    }

  } .elsewhen (io.funct7 === 1.U) {

    when (io.aluop  === 1.U) { // condition for when aluop is 0
        when (io.funct3 === 0.U) { // individual conditions
          io.operation := 6.U
        } .elsewhen (io.funct3 === 1.U) {
           io.operation := 7.U
        } .elsewhen (io.funct3 === 2.U) {
           io.operation := 8.U
        } .elsewhen (io.funct3 === 3.U) {
           io.operation := 24.U
        } .elsewhen (io.funct3 === 4.U) {
          io.operation := 11.U
        } .elsewhen (io.funct3 === 5.U) {
          io.operation := 10.U
        } .elsewhen (io.funct3 === 6.U) {
          io.operation := 28.U
        } .otherwise {
          io.operation := 27.U
        }
    } .otherwise { // aluop is 1
        when (io.funct3 === 0.U) { // individual conditions
          io.operation := 5.U
        } .elsewhen (io.funct3 === 4.U) {
          io.operation := 9.U
        } .elsewhen (io.funct3 === 5.U) {
          io.operation := 6.U
        } .elsewhen (io.funct3 === 6.U) {
          io.operation := 26.U
        } .otherwise {
          io.operation := 25.U
        }
    } // end of aluop 1

  } .otherwise { // when funct7 == 32
      when (io.aluop === 1.U) { // aluop == 1
        when (io.funct3 === 0.U) {
          io.operation := 4.U
        } .otherwise {
          io.operation := 16.U
        }
      } .otherwise {
        when (io.funct3 === 0.U) {
          io.operation := 2.U
        } .otherwise {
          io.operation := 17.U
        }
      } // end of aluop = 3
  } // end of funct7 vals
                                                                                      }

