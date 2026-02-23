package org.misu.finalproject.controller.commands;


import org.misu.finalproject.controller.ConcurrentController;
import org.misu.finalproject.controller.Controller;
import org.misu.finalproject.controller.commands.exception.CommandException;
import org.misu.finalproject.model.expresion.exception.CompilerException;
import org.misu.finalproject.model.interpretor.BasicProgramState;
import org.misu.finalproject.model.interpretor.table.TypeEnvironment;
import org.misu.finalproject.model.statement.Statement;
import org.misu.finalproject.repository.ConcurrentRepository;

import java.io.IOException;

public class RunExampleCommand extends Command{
    private final Statement program;
    public RunExampleCommand(String key, String description, Statement program) {
        super(key, description);
        this.program = program;
    }

    @Override
    public void execute() throws CommandException {
        // Compile checks
        try {
            program.typecheck(new TypeEnvironment());
        } catch (CompilerException e) {
            throw new CommandException(e);
        }

        Controller crt = ConcurrentController.newInstance(new ConcurrentRepository(  BasicProgramState.newInstance(program.deepCopy()), "logFile.txt"));
        try {
            crt.allStep(true);
        } catch (IOException | InterruptedException e) {
            throw new CommandException(e);
        }

    }
}
