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
