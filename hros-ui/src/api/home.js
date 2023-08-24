import request from "@/request.js";

export const GetDynamicRoutes = (id) => {
    return request.get('/user/activityRoute/' + id);
};