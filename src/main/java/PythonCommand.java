import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PythonCommand {

    private final String pathToPythonExecutable;

    PythonCommand(String pathToPythonExecutable) {
        this.pathToPythonExecutable = pathToPythonExecutable;
    }

    public String call() {
        Thread timePrinterThread = new Thread(new TimePrinter());
        String[] command = {pathToPythonExecutable, "-m", "timeit", "-r", "10"};

        Process process;
        try {
            process = new ProcessBuilder(command).start();
        } catch (IOException e) {
            return "Error! You probably entered the wrong path to the python executable. " +
                    "Error message: " + e.getMessage();
        }

        timePrinterThread.start();

        try {
            process.waitFor();
            timePrinterThread.interrupt();
            timePrinterThread.join();
        } catch (InterruptedException ignored) {
        }

        return getCommandResult(process);
    }

    private String getCommandResult(Process process) {
        if (process.exitValue() != 0) {
            return "Error! The process exited with a nonzero return code";
        }

        StringBuilder result = new StringBuilder();
        try (BufferedReader reader
                     = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (IOException e) {
            return "Error while reading from process input stream. " +
                    "Error message: " + e.getMessage();
        }

        return result.toString();
    }
}
