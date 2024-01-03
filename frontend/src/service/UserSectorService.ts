
import axios from "../api";
import {UserSector} from "../types/UserSector";

export const SaveUserSector = async(userSector:UserSector) => {
    return await axios.post("user-sectors", userSector)
};