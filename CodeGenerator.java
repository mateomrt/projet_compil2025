import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

import Asm.UAL;
import Asm.UALi;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import Asm.Program;
import Type.Type;
import Type.UnknownType;

public class CodeGenerator  extends AbstractParseTreeVisitor<Program> implements grammarTCLVisitor<Program> {

    private int nbRegister = 3;
    private Dictionary<String, Integer> varToReg = new Hashtable<>();
    private Map<UnknownType,Type> types;


    /**
     * Constructeur
     * @param types types de chaque variable du code source
     */
    public CodeGenerator(Map<UnknownType, Type> types) {
        this.types = types;
    }

    public int getNewRegister() {
        nbRegister++;
        return nbRegister;
    }

    @Override
    public Program visitNegation(grammarTCLParser.NegationContext ctx) {
        Program pCtx = visit(ctx.getChild(0));
        int addr = this.nbRegister;
        Program p = new Program();
        p.addInstructions(pCtx);
        p.addInstruction(new UALi(UALi.Op.SUB, getNewRegister(), addr, 1));
        int tempAddr = this.nbRegister;
        p.addInstruction(new UAL(UAL.Op.SUB, getNewRegister(), 0, tempAddr));
        return p;
    }

    @Override
    public Program visitComparison(grammarTCLParser.ComparisonContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitComparison'");
    }

    @Override
    public Program visitOr(grammarTCLParser.OrContext ctx) {
        Program pLeft = visit(ctx.getChild(0));
        int leftAddr = this.nbRegister;
        Program pRight = visit(ctx.getChild(1));
        int rightAddr = this.nbRegister;
        Program p = new Program();
        p.addInstructions(pLeft);
        p.addInstructions(pRight);
        p.addInstruction(new UALi(UALi.Op.OR, getNewRegister(), leftAddr, rightAddr));
        return p;
    }

    @Override
    public Program visitOpposite(grammarTCLParser.OppositeContext ctx) {
        Program pCtx = visit(ctx.getChild(0));
        int addr = this.nbRegister;
        Program p = new Program();
        p.addInstructions(pCtx);
        p.addInstruction(new UAL(UAL.Op.SUB, getNewRegister(), 0, addr));
        return p;
    }

    @Override
    public Program visitInteger(grammarTCLParser.IntegerContext ctx) {
        int value = Integer.parseInt(ctx.getText());
        Program p = new Program();
        p.addInstruction(new UALi(UALi.Op.ADD, getNewRegister(), 0, value));
        return p;
    }

    @Override
    public Program visitTab_access(grammarTCLParser.Tab_accessContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitTab_access'");
    }

    @Override
    public Program visitBrackets(grammarTCLParser.BracketsContext ctx) {
        return visit(ctx.getChild(0));
    }

    @Override
    public Program visitCall(grammarTCLParser.CallContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitCall'");
    }

    @Override
    public Program visitBoolean(grammarTCLParser.BooleanContext ctx) {
        int value = Integer.parseInt(ctx.getText());
        Program p = new Program();
        p.addInstruction(new UALi(UALi.Op.ADD, getNewRegister(), 0, value));
        return p;
        //throw new UnsupportedOperationException("Unimplemented method 'visitBoolean'");
    }

    @Override
    public Program visitAnd(grammarTCLParser.AndContext ctx) {
        Program pLeft = visit(ctx.getChild(0));
        int leftAddr = this.nbRegister;
        Program pRight = visit(ctx.getChild(1));
        int rightAddr = this.nbRegister;
        Program p = new Program();
        p.addInstructions(pLeft);
        p.addInstructions(pRight);
        p.addInstruction(new UAL(UAL.Op.AND, getNewRegister(), leftAddr, rightAddr));
        return p;
    }

    @Override
    public Program visitVariable(grammarTCLParser.VariableContext ctx) {
        String varName = ctx.getText();
        Program p = new Program();
        p.addInstruction(new UALi(UALi.Op.ADD, getNewRegister(), 0, varToReg.get(varName)));
        return p;
    }

    @Override
    public Program visitMultiplication(grammarTCLParser.MultiplicationContext ctx) {
        Program pLeft = visit(ctx.getChild(0));
        int leftAddr = this.nbRegister;
        Program pRight = visit(ctx.getChild(1));
        int rightAddr = this.nbRegister;
        Program p = new Program();
        p.addInstructions(pLeft);
        p.addInstructions(pRight);
        p.addInstruction(new UAL(UAL.Op.MUL, getNewRegister(), leftAddr, rightAddr));
        return p;
    }

    @Override
    public Program visitEquality(grammarTCLParser.EqualityContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitEquality'");
    }

    @Override
    public Program visitTab_initialization(grammarTCLParser.Tab_initializationContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitTab_initialization'");
    }

    @Override
    public Program visitAddition(grammarTCLParser.AdditionContext ctx) {
        Program pLeft = visit(ctx.getChild(0));
        int leftAddr = this.nbRegister;
        Program pRight = visit(ctx.getChild(1));
        int rightAddr = this.nbRegister;
        Program p = new Program();
        p.addInstructions(pLeft);
        p.addInstructions(pRight);
        String ope = ctx.getText();
        if(ope.equals("+")) {
            p.addInstruction(new UAL(UAL.Op.ADD, getNewRegister(), leftAddr, rightAddr));
        } else {
            // ope == "-"
            p.addInstruction(new UAL(UAL.Op.SUB, getNewRegister(), leftAddr, rightAddr));
        }
        return p;
    }

    @Override
    public Program visitBase_type(grammarTCLParser.Base_typeContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitBase_type'");
    }

    @Override
    public Program visitTab_type(grammarTCLParser.Tab_typeContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitTab_type'");
    }

    @Override
    public Program visitDeclaration(grammarTCLParser.DeclarationContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitDeclaration'");
    }

    @Override
    public Program visitPrint(grammarTCLParser.PrintContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitPrint'");
    }

    @Override
    public Program visitAssignment(grammarTCLParser.AssignmentContext ctx) {
        // TODO Auto-generated method stub

        throw new UnsupportedOperationException("Unimplemented method 'visitAssignment'");
    }

    @Override
    public Program visitBlock(grammarTCLParser.BlockContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitBlock'");
    }

    @Override
    public Program visitIf(grammarTCLParser.IfContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitIf'");
    }

    @Override
    public Program visitWhile(grammarTCLParser.WhileContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitWhile'");
    }

    @Override
    public Program visitFor(grammarTCLParser.ForContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitFor'");
    }

    @Override
    public Program visitReturn(grammarTCLParser.ReturnContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitReturn'");
    }

    @Override
    public Program visitCore_fct(grammarTCLParser.Core_fctContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitCore_fct'");
    }

    @Override
    public Program visitDecl_fct(grammarTCLParser.Decl_fctContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitDecl_fct'");
    }

    @Override
    public Program visitMain(grammarTCLParser.MainContext ctx) {
        // TODO Auto-generated method stub
        Program p = new Program();
        p.addInstruction(new UAL(UAL.Op.XOR, 0, 0, 0));
        throw new UnsupportedOperationException("Unimplemented method 'visitMain'");
    }

        
}
