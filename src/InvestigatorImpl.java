import reflection.api.Investigator;

import java.lang.reflect.*;
import java.util.HashSet;
import java.util.Set;

public class InvestigatorImpl implements Investigator{
    private Class suspect;
    private Object instance;

    public InvestigatorImpl() {}

    public InvestigatorImpl(Object anInstanceOfSomething) {
        load(anInstanceOfSomething);
    }

    @Override
    public void load(Object anInstanceOfSomething) {
        suspect = anInstanceOfSomething.getClass();
        instance = anInstanceOfSomething;
    }

    @Override
    public int getTotalNumberOfMethods() {
        return suspect.getDeclaredMethods().length;
    }

    @Override
    public int getTotalNumberOfConstructors() {
        return suspect.getDeclaredConstructors().length;
    }

    @Override
    public int getTotalNumberOfFields() {
        return suspect.getDeclaredFields().length;
    }

    @Override
    public Set<String> getAllImplementedInterfaces() {
        Set<String> implementedInterfaceNames = new HashSet<>();
        for(Class<?> implementedInterface : suspect.getInterfaces())
            implementedInterfaceNames.add(implementedInterface.getSimpleName());
        return implementedInterfaceNames;
    }

    @Override
    public int getCountOfConstantFields() {
        int count = 0;
        Field[] fields = suspect.getDeclaredFields();
        for(Field field : fields)
            if(Modifier.isFinal(field.getModifiers()))
                count++;
        return count;
    }

    @Override
    public int getCountOfStaticMethods() {
        int count = 0;
        Method[] methods = suspect.getDeclaredMethods();
        for(Method method : methods)
            if(Modifier.isStatic(method.getModifiers()))
                count++;
        return count;
    }

    @Override
    public boolean isExtending() {
        Class<?> superclass = suspect.getSuperclass();
        return superclass != null && superclass != Object.class;
    }

    @Override
    public String getParentClassSimpleName() {
        return isExtending() ? suspect.getSuperclass().getSimpleName() : null;
    }

    @Override
    public boolean isParentClassAbstract() {
        return isExtending() && Modifier.isAbstract(suspect.getSuperclass().getModifiers());
    }

    //Recursive helper method for the getNamesOfAllFieldsIncludingInheritanceChain() method
    private Set<String> getNamesOfAllFieldsIncludingInheritanceChain(Class<?> clazz)
    {
        Set<String> fieldNames = new HashSet<>();
        Class<?> superclass = clazz.getSuperclass();
        for(Field field : clazz.getDeclaredFields())
            fieldNames.add(field.getName());
        if(superclass != null && superclass != Object.class)
            fieldNames.addAll(getNamesOfAllFieldsIncludingInheritanceChain(superclass));
        return fieldNames;
    }

    @Override
    public Set<String> getNamesOfAllFieldsIncludingInheritanceChain() {
        return getNamesOfAllFieldsIncludingInheritanceChain(suspect);
    }

    @Override
    public int invokeMethodThatReturnsInt(String methodName, Object... args) {
        for(Method method : suspect.getDeclaredMethods())
            if(method.getName().equals(methodName)) {
                try {
                    return (int) method.invoke(instance, args);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        return 0;
    }

    @Override
    public Object createInstance(int numberOfArgs, Object... args) {
        for(Constructor<?> constructor : suspect.getDeclaredConstructors())
            if(constructor.getParameterCount() == numberOfArgs) {
                try {
                    return constructor.newInstance(args);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        return null;
    }

    @Override
    public Object elevateMethodAndInvoke(String name, Class<?>[] parametersTypes, Object... args) {
        Method methodToElevateAndInvoke;
        try {
             methodToElevateAndInvoke = suspect.getDeclaredMethod(name, parametersTypes);
             methodToElevateAndInvoke.setAccessible(true);
             return methodToElevateAndInvoke.invoke(instance, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Recursive helper method for the getInheritanceChain(String) method
    private String getInheritanceChain(Class<?> clazz, String delimiter)
    {
        Class<?> superclass = clazz.getSuperclass();
        if(superclass == null)
            return clazz.getSimpleName();
        else return getInheritanceChain(superclass, delimiter) + delimiter + clazz.getSimpleName();
    }

    @Override
    public String getInheritanceChain(String delimiter) {
        return getInheritanceChain(suspect, delimiter);
    }
}
