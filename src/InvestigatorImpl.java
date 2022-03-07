import reflection.api.Investigator

import java.util.Set;

public class InvestigatorImpl implements Investigator{
    private Class<?> suspect;

    public InvestigatorImpl() {}

    public InvestigatorImpl(Object anInstanceOfSomething) {
        load(anInstanceOfSomething);
    }

    @Override
    public void load(Object anInstanceOfSomething) {
        suspect = anInstanceOfSomething.getClass();
    }

    @Override
    public int getTotalNumberOfMethods() {
        return suspect.getDeclaredMethods().length;
    }

    @Override
    public int getTotalNumberOfConstructors() {
        return 0;
    }

    @Override
    public int getTotalNumberOfFields() {
        return 0;
    }

    @Override
    public Set<String> getAllImplementedInterfaces() {
        return null;
    }

    @Override
    public int getCountOfConstantFields() {
        return 0;
    }

    @Override
    public int getCountOfStaticMethods() {
        return 0;
    }

    @Override
    public boolean isExtending() {
        return false;
    }

    @Override
    public String getParentClassSimpleName() {
        return null;
    }

    @Override
    public boolean isParentClassAbstract() {
        return false;
    }

    @Override
    public Set<String> getNamesOfAllFieldsIncludingInheritanceChain() {
        return null;
    }

    @Override
    public int invokeMethodThatReturnsInt(String methodName, Object... args) {
        return 0;
    }

    @Override
    public Object createInstance(int numberOfArgs, Object... args) {
        return null;
    }

    @Override
    public Object elevateMethodAndInvoke(String name, Class<?>[] parametersTypes, Object... args) {
        return null;
    }

    @Override
    public String getInheritanceChain(String delimiter) {
        return null;
    }
}
