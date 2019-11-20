package planning;

import example.HealthCare;

import java.util.*;

public class PlanningProblem {
    protected State state_init;
    protected State state_goal;

    protected List<Action> possible_actions;
        
    protected int countDepth;
    protected int countDepthIterative;
    protected int countBreadth;

    public PlanningProblem(State ini, State goal, List<Action> act){
        this.state_init = ini;
        this.state_goal = goal;
        this.possible_actions = act;
        this.countDepth = 0;
        this.countDepthIterative = 0;
        this.countBreadth = 0;
    }

    public State getStateInit(){
        return this.state_init;
    }

    public State getStateGoal(){
        return this.state_goal;
    }

    public List<Action> getPossibleActions(){
        return this.possible_actions;
    }

    public int getCountDepth(){
        return this.countDepth;
    }

    public int getCountDepthIterative(){
        return this.countDepthIterative;
    }

    public int getCountBreadth(){
        return this.countBreadth;
    }

    /**
     * Trouve un Stack (une pile) d'Action permettant d'atteindre le State final du PlanningProblem en utilisant la recherche en profondeur
     * @param actual_state
     * @param plan_d_action
     * @param closed
     * @return un Stack (une pile) d'Action
     */
    public Stack<Action> depthSearch(State actual_state, Stack<Action> plan_d_action, List<State> closed){
        this.countDepth = 0;
        if( actual_state.satisfies(this.state_goal) ){
            return plan_d_action;
        }
        else{
            for(Action act: this.possible_actions){
                if( actual_state.is_applicable(act) ){
                    this.countDepth = this.countDepth + 1;
                    State nextState = actual_state.apply(act);
                    
                    if( !closed.contains(nextState) ){
                        plan_d_action.push(act);
                        closed.add(nextState);
                        Stack<Action> sousPlan = depthSearch(actual_state, plan_d_action, closed);
                        
                        if( !sousPlan.empty() ){
                            return sousPlan;
                        }
                        else{
                            sousPlan.pop();
                        }
                    }
                }
            }
            return null;
        }
    }

    /**
     * Trouve un Stack (une pile) d'Actions permettant d'atteindre le State final du PlanningProblem en utilisant la recherche en largeur
     * @return un Stack (une pile) d'Actions
     */
    public List<Action> breadthSearch(){
        Map<State,State> father = new HashMap();
        Map<State,Action> plan  = new HashMap();
        List<State> closed      = new ArrayList();
        Queue<State> open       = new ArrayDeque();
        
        open.add(this.state_init);
        father.put(this.state_init, null);

        while( !open.isEmpty() ){
            State state = open.remove();
            closed.add(state);

            for(Action act : this.possible_actions){
                if( state.is_applicable(act) ){
                    this.countBreadth = this.countBreadth +1;
                    State next = ((State) state.clone()).apply(act);
                    next.apply(HealthCare.HEALOMAX());


                    System.out.println("1");
                    if( !closed.contains(next) && !open.contains(next) ){
                        System.out.println("2");
                        father.put(next, state);
                        plan.put(next, act);

                        /**
                        if (!next.satisfies(this.state_goal)){
                            System.out.println(next);
                            System.out.println(this.state_goal);
                        }**/


                        if( next.satisfies(this.state_goal) ){
                            System.out.println("3");
                            return getBreadthSearchPlan(father, plan, next);
                        }
                        else{
                            open.add(next);
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Permet de reconstruire le Stack (la pile) d'action dans le bon ordre
     * @param father (Map liant un State à son State père)
     * @param actions (Map liant un State à son Action permettant de l'atteindre)
     * @param goal (State )
     * @return un Stack (une pile) d'Action
     */
    protected static List<Action> getBreadthSearchPlan(Map<State,State> father, Map<State,Action> actions, State goal){
        List<Action> list = new ArrayList();
        //System.out.println("Action size : " + actions.size());
        //System.out.println("Father size : " + father.size());


        while( goal != null ){
            //System.out.println("father goal : " + Boolean.toString(father.get(goal) != null));
            //System.out.println("Actions goal : " + Boolean.toString(actions.get(goal) != null));

            if (father.containsKey(goal) && actions.get(father.get(goal)) != null) {
                list.add(actions.get(goal));
                goal = father.get(goal);
                if (goal == null) System.out.println("NULL");
            }
            else {
                return list;
            }
        }
        return list;
    }

    @Override
    public String toString() {
        return "PlanningProblem{" +
                "state_init=" + state_init +
                ", state_goal=" + state_goal +
                ", possible_actions=" + possible_actions +
                ", countDepth=" + countDepth +
                ", countDepthIterative=" + countDepthIterative +
                ", countBreadth=" + countBreadth +
                '}';
    }
}