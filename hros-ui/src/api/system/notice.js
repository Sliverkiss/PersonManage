import request from "@/request.js";

export function getNoticeList() {
    return request.get('/admin/notice/list');
}

export function addNotice(query) {
    return request.post('/admin/notice/save', query);
}

export function updateNotice(query) {
    return request.put('/admin/notice/update', query);
}

export function delNotice(id) {
    return request.delete('/admin/notice/delete/' + id);

}
