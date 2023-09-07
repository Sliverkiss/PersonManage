import request from "@/request.js";

export function getPostList() {
    return request.get('/admin/department/post/list');
}

export function addPost(query) {
    return request.post('/admin/department/post/save', query);
}

export function updatePost(query) {
    return request.put('/admin/department/post/update', query);
}

export function delPost(id) {
    return request.delete('/admin/department/post/delete/' + id);

}
