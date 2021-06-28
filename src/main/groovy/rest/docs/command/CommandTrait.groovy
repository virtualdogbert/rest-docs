package rest.docs.command

import grails.validation.Validateable

trait CommandTrait implements Validateable{
/**
     * Used to exact the properties of the command object as a Map
     * @return a map of all of the properties that make up the Command Object
     */
    Map toMap() {
        commandProperties()
    }

    /**
     * Used to return the property/values of the domain object excluding those that are injected by the
     * @return a map of all of the property/values
     */
    Map commandProperties(List<String> exclude = []) {
        List<String> excludeProperties =['metaClass', 'class', 'constraints', 'constraintsMap', 'errors']
        exclude.addAll(exclude)

        return this.properties.findAll{ Map.Entry<Object,Object> entry->
            !excludeProperties.contains(entry.key)
        }
    }

    List<String> commandPropertyNames (List<String> exclude = []){
        return commandProperties(exclude).keySet()
    }

    int nearest(number, numbers){
        int midPoint = 0
        int difference = 0

        if(number < numbers.first()){
            return numbers.first()
        }

        if(number > numbers.last()){
            return numbers.last()
        }

        for(int x = 0; x< numbers.size ; x++){
            int min = numbers[x]
            int max = numbers[x + 1]

            if(number == min){
               return min
            }

            if(number > max){
                continue
            }

            difference = max - min
            midPoint = min + (difference)/2

            if(difference %2 != 0){
                midPoint++
            }

            if(number < midPoint){
                return min
            }

            return max
        }
    }
}