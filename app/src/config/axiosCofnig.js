import ax from "axios";

const axiosConfig = {
    baseURL: 'http://127.0.0.1:8080/',
    timeout: 30000,

};

export const axios = ax.create(axiosConfig)