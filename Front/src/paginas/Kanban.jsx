import React, { useCallback, useState } from 'react';
import ScrollView from 'devextreme-react/scroll-view';
import Sortable from 'devextreme-react/sortable';
import { employees } from './data.js';

import '../kanban.css';

function simplifyTask(objectTasks){
    const simplifiedTask = (objectTasks || []).map( task =>{
        return {
            id : task.id,
            title : task.title,
            description : task.description,
            taskType : task.taskType.name,
            taskStatus: task.taskStatus.name,
            user : task.user.id,
            priority: task.priority
        };
    } );
    return simplifiedTask;
}

function simplifyTaskStatus(objectStatuses){
    const simplifiedStatuses = (objectStatuses || []).map( taskStatus => {
        return {
            name : taskStatus.name
        };
    } );
    return simplifiedStatuses;
}

function getLists(statusArray, taskArray) {
    const tasksMap = taskArray.reduce((result, task) => {
        if (result[task.taskStatus]) {
            result[task.taskStatus].push(task);
        } else {
            result[task.taskStatus] = [task];
        }
        return result;
    }, {});
    return statusArray.map((status) => tasksMap[status.name]);
}
function getEmployeesMap(employeesArray) {
    return employeesArray.reduce((result, employee) => {
        result[employee.ID] = employee.Name;
        return result;
    }, {});
}
function removeItem(array, removeIdx) {
    return array.filter((_, idx) => idx !== removeIdx);
}
function insertItem(array, item, insertIdx) {
    const newArray = [...array];
    newArray.splice(insertIdx, 0, item);
    return newArray;
}
function reorderItem(array, fromIdx, toIdx) {
    const item = array[fromIdx];
    const result = removeItem(array, fromIdx);
    return insertItem(result, item, toIdx);
}

const employeesRecord = getEmployeesMap(employees);
const Card = ({ task, employeesMap }) => (
    <div className="card dx-card">
        <div className={`card-priority priority-${task.priority}`}></div>
        <div className="card-subject">{task.title}</div>
        <div className='card-info'>
            <a>Descripción:</a> {task.description}<br></br>
            <a>Tipo:</a> {task.taskType}
        </div>
        <div className="card-assignee">
            <a>Encargado:</a> {employeesMap[task.user]}
        </div>
    </div>
);
const List = ({
    title, index, tasks, employeesMap, onTaskDrop,
}) => (
    <div className="list">
        <div className="list-title">{title}</div>
        <ScrollView
            className="scrollable-list"
            direction="vertical"
            showScrollbar="always"
        >
            <Sortable
                className="sortable-cards"
                group="cardsGroup"
                data={index}
                /*onReorder={onTaskDrop}
                onAdd={onTaskDrop}*/
            >
                {(tasks || []).map((task) => (
                    <Card
                        key={task.id}
                        task={task}
                        employeesMap={employeesMap}
                    ></Card>
                ))}
            </Sortable>
        </ScrollView>
    </div>
);
function Kanban( props ) {

    const taskStatuses = simplifyTaskStatus(props.taskStatus);
    const taskList = simplifyTask(props.tasks);

    const [statuses, setStatuses] = useState(taskStatuses);
    const [lists, setLists] = useState(getLists(taskStatuses, taskList));
    const onListReorder = useCallback(({ fromIndex, toIndex }) => {
        setLists((state) => reorderItem(state, fromIndex, toIndex));
        setStatuses((state) => reorderItem(state, fromIndex, toIndex));
    }, []);
    /*
    const onTaskDrop = useCallback(
        ({
            fromData, toData, fromIndex, toIndex,
        }) => {
            const updatedLists = [...lists];
            const item = updatedLists[fromData][fromIndex];
            updatedLists[fromData] = removeItem(updatedLists[fromData], fromIndex);
            updatedLists[toData] = insertItem(updatedLists[toData], item, toIndex);
            setLists(updatedLists);
        },
        [lists],
    );*/

    return (
        <div id="kanban">
            <ScrollView
                className="scrollable-board"
                direction="horizontal"
                showScrollbar="always"
            >
                <Sortable
                    className="sortable-lists"
                    itemOrientation="horizontal"
                    handle=".list-title"
                    onReorder={onListReorder}
                >
                    {lists.map((tasks, listIndex) => {
                        const status = statuses[listIndex];
                        return (
                            <List
                                key={status.name}
                                title={status.name}
                                index={listIndex}
                                tasks={tasks}
                                employeesMap={employeesRecord}
                                /*onTaskDrop={onTaskDrop}*/
                            ></List>
                        );
                    })}
                </Sortable>
            </ScrollView>
        </div>
    );
}
export default Kanban;