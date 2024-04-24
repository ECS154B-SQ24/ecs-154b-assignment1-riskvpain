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
   when (io.funct7 === "b00000".U) { // when funct7 = 0.U
    when (io.aluop  === "b00001".U) { // condition for when aluop is 0
        when (io.funct3 === "b00000".U) { // individual conditions
          io.operation := "b00001".U
        } .elsewhen (io.funct3 === "b00001".U) {
           io.operation := "b10010".U
        } .elsewhen (io.funct3 === "b00010".U) {
           io.operation := "b10110".U
        } .elsewhen (io.funct3 === "b00011".U) {
           io.operation := "b10111".U
        } .elsewhen (io.funct3 ==="b00100".U) {
          io.operation := "b01111".U
        } .elsewhen (io.funct3 === "b00101".U) {
          io.operation := "b10100".U
        } .elsewhen (io.funct3 === "b00110".U) {
          io.operation := "b01110".U
        } .otherwise {
          io.operation := "b01101".U
        }
    } .otherwise { // aluop is 1
        when (io.funct3 === "b00000".U) { // individual conditions
          io.operation := "b00000".U
        } .elsewhen (io.funct3 === "b00001".U) {
          io.operation := "b10011".U
        } otherwise {
          io.operation := "b10101".U
        }
     
     }
       

  } .elsewhen (io.funct7 === "b00001".U) {

    when (io.aluop  === "b00001".U) { // condition for when aluop is 0
        when (io.funct3 === "b00000".U) { // individual conditions
          io.operation := "b00110".U
        } .elsewhen (io.funct3 === "b00001".U) {
           io.operation := "b00111".U
        } .elsewhen (io.funct3 === "b00010".U) {
           io.operation := "b11000".U
        } .elsewhen (io.funct3 === "b00011".U) {
           io.operation := "b01000".U
        } .elsewhen (io.funct3 === "b00100".U) {
          io.operation := "b01011".U // this is diff
        } .elsewhen (io.funct3 === "b00101".U) {
          io.operation := "b01010".U // this is diff
        } .elsewhen (io.funct3 === "b00110".U) {
          io.operation := "b11100".U
        } .otherwise {
          io.operation := "b11011".U
        }
    } .otherwise { // aluop is 1
        when (io.funct3 === "b00000".U) { // individual conditions
          io.operation := "b00101".U
        } .elsewhen (io.funct3 === "b00100".U) {
          io.operation := "b01001".U
        } .elsewhen (io.funct3 === "b00101".U) {
          io.operation := "b01100".U
        } .elsewhen (io.funct3 === "b00110".U) {
          io.operation := "b11010".U
        } .otherwise {
          io.operation := "b11001".U
        }
    } // end of aluop 1

  } .otherwise { // when funct7 == 32
      when (io.aluop === "b00001".U) { // aluop == 1
        when (io.funct3 === "b00000".U) {
          io.operation := "b00100".U
        } .otherwise {
            io.operation := "b10000".U
        }
      } .otherwise {
        when (io.funct3 === "b00000".U) {
          io.operation := "b00010".U
        } .otherwise {
          io.operation := "b10001".U
        }
      } // end of aluop = 3
  } // end of funct7 vals
}                   
