/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Models;

/**
 *
 * @author josed
 */
public enum CommandType {    
    ATAQUE {
        @Override
        public Command create(String[] params) {
            return new AttackCommand(params);
        }
    },
    
    ALL {  //MENSAJE PARA TODOS
        @Override
        public Command create(String[] params) {
            return new MsgAllCommand(params);
        }
    },
    
    DM {  //MENSAJE PRIVADO
        @Override
        public Command create(String[] params) {
            return new PrivateMsgCommand(params);
        }
    },
    
    
    RECEIVEATTACK {
        @Override
        public Command create(String[] params) {
            return new ReceiveAttackCommand(params);
        }
    },
    
    ASSIGNTARGET {
        @Override
        public Command create(String[] params) {
            return new AssignTargetCommand(params);
        }
    },
    
    DRAW {
        @Override
        public Command create(String[] params) {
            return new DrawCommand(params);
        }
    },
    
    RENDIRSE{
        @Override
        public Command create(String[] params) {
            return new GiveupCommand(params);
        }
    },
    PASS{
        @Override
        public Command create(String[] params) {
            return new PassCommand(params);
        }
    },
    RECARGAR{
        @Override
        public Command create(String[] params) {
            return new RechargeGunsCommand(params);
        }
    },
    COMODIN{
        @Override
        public Command create(String[] params) {
            return new UseJokerCommand(params);
        }
    },
    CONSULTAR{
        @Override
        public Command create(String[] params) {
            return new SelectPlayerCommand(params);
        }
        
    };

    public abstract Command create(String[] params);

    public static CommandType from(String raw) {
        try {
            return CommandType.valueOf(raw.trim().toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }
}

    

