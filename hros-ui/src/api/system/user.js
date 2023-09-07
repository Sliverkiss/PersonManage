import request from "@/request.js";

export function addUser(query) {
    return request.post('/user/save', query);
}

export function updateUser(query) {
    return request.put('/user/update', query);
}

export function delUser(id) {
    return request.delete('/user/delete/' + id);

}

export function changeUser(query) {
    return request.post('/user/change', query);

}

export function getUser(query) {
    return request.post('/user/userInfo/' + query);
}
