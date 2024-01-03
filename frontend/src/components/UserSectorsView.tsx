import {Fragment, useEffect, useState} from "react";
import {
    Autocomplete,
    Box,
    Button,
    Checkbox,
    CircularProgress,
    FormControlLabel,
    Paper, Skeleton,
    TextField,
    Typography
} from "@mui/material";
import {Sector} from "../types/Sector";
import {GetAllSectors} from "../service/SectorService";
import {AxiosResponse} from "axios";
import CheckBoxIcon from "@mui/icons-material/CheckBox"
import CheckBoxOutlineBlankIcon from '@mui/icons-material/CheckBoxOutlineBlank';
import {UserSector} from "../types/UserSector";
import {SaveUserSector} from "../service/UserSectorService";


const icon = <CheckBoxOutlineBlankIcon fontSize="small"/>;
const checkedIcon = <CheckBoxIcon fontSize="small"/>;

const initialFormData = {
    name: '',
    sectors: [],
    agreeToTerms: false
};

export default function UserSectorsView() {
    const [sectors, setSectors] = useState<Sector[]>([]);
    const [formData, setFormData] = useState<UserSector>(initialFormData);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        GetAllSectors().then((response: AxiosResponse) => {
            setSectors(response.data);
        })
    }, []);

    useEffect(() => {
        console.log(formData);
    }, [loading]);

    const onSectorsChange = (event: any, newValue: Sector[], reason: string) => {
        setFormData({
            ...formData,
            sectors: newValue
        });
    }

    const onNameChange = (event: any) => {
        setFormData({
            ...formData,
            name: event.target.value
        });
    }

    const onAgreeChange = (event: any) => {
        console.log(event, event.target.value, event.target.value === 'on');
        setFormData({
            ...formData,
            agreeToTerms: event.target.checked
        });
    }

    const onSubmit = (event: any) => {
        event.preventDefault();
        setLoading(true);
        console.log(formData);
        SaveUserSector(formData).then((response: AxiosResponse) => {
            setTimeout(() => setLoading(false), 1000);
            setTimeout(() => setFormData(response.data), 1300);
        });
    }

    return (
        <Fragment>
            <Paper sx={{flex: 1}} elevation={0}>
                <Typography>
                    {loading ?
                        <Skeleton/> : "Please enter your name and pick the Sectors you are currently involved in."}
                </Typography>
                <Box component="form" onSubmit={onSubmit} autoComplete="off">
                    {loading ? <Skeleton height={40} width={223}/> :
                        <TextField sx={{mt: 2, mb: 2}} size="small" onChange={onNameChange} value={formData.name} required id="name"
                                   label="Name"/>
                    }
                    {loading ? <Skeleton height={40} width={620}/> :
                        <Autocomplete
                            multiple
                            size="small"
                            id="sectors"
                            disableCloseOnSelect
                            getOptionLabel={(option) => option.name}
                            getOptionKey={(option) => option.val}
                            isOptionEqualToValue={(option, value) => option.val === value.val}
                            value={formData.sectors}
                            onChange={onSectorsChange}
                            renderOption={(props, option, {selected}) => (
                                <li {...props}>
                                    <Checkbox
                                        icon={icon}
                                        checkedIcon={checkedIcon}
                                        style={{marginRight: 8}}
                                        checked={selected}
                                    />
                                    {/* no-break space in unicode */
                                        '\u00A0'.repeat((option.level * 4))}{option.name
                                }
                                </li>
                            )}
                            renderInput={(params) => (
                                <TextField required {...params} label="Sectors"
                                           inputProps={{...params.inputProps, required: formData.sectors.length < 1}}/>
                            )}
                            options={sectors}/>
                    }
                    {loading ? (
                        <Skeleton width={220}>
                            <Typography>.</Typography>
                        </Skeleton>
                    ) : (
                        <Fragment>
                            <FormControlLabel
                                required label="Agree to terms"
                                control={<Checkbox checked={formData.agreeToTerms} onChange={onAgreeChange} id="agree"/>}/>
                            <Button type="submit">Save</Button>
                        </Fragment>
                    )}
                </Box>
            </Paper>
            <Paper sx={{flex: 1}} elevation={0} />
        </Fragment>
    )
}