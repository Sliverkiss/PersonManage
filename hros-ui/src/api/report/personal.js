import request from "@/request.js";

export function genderPie() {
    return request.get('/admin/report/gender');
}

export function workStatePie() {
    return request.get('/admin/report/workState');
}

export function TiptopDegreePie() {
    return request.get('/admin/report/tiptopDegree');
}

export function engageFormPie() {
    return request.get('/admin/report/engageForm');
}

export function departmentBar() {
    return request.get('/admin/report/department');
}

export function politicBar() {
    return request.get('/admin/report/politic');
}