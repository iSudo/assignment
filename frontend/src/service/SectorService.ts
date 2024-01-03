import {Sector} from "../types/Sector";
import axios from "../api";


export const SaveSector = async(sector:Sector) => {
    return await axios.post("sectors", sector)
};

export const GetAllSectors = async() => {
    return await axios.get("sectors")
}