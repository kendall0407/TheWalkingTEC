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
    CREAR {
        @Override
        public Command create(String[] params) {
            return new PrivateMsgCommand(params);
        }
    },
    
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
            return new PrivateMsgCommand(params);
        }
    },
    
    ASSIGNTARGET {
        @Override
        public Command create(String[] params) {
            return new PrivateMsgCommand(params);
        }
    }
    
    
    ;

    public abstract Command create(String[] params);

    public static CommandType from(String raw) {
        try {
            return CommandType.valueOf(raw.trim().toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }
}

    

