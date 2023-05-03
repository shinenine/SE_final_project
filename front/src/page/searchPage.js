import "./searchPage.css"
import axios from "axios";
import {CloseCircleOutlined, SearchOutlined} from "@ant-design/icons";
import {useState} from "react";
import {Card, Drawer, Spin} from "antd";



const SearchPage = () => {
    const [hasFocus, setHasFocus] = useState(false)
    const [query, setQuery] = useState('')

    const [hasSearch, setHasSearch] = useState(false)
    const [hasSimilarSearch, setHasSimilarSearch] = useState(false)

    const [searchResult, setSearchResult] = useState([])
    const [similarResult, setSimilarResult] = useState([])


    const [searchLoading, setSearchLoading] =useState(true)
    const [similarSearchLoading, setSimilarSearchLoading] =useState(true)

    const [open, setOpen] = useState(false)
    const [queryHistory, setQueryHistory] = useState([])

    const handleChange = (event) => {
        setQuery(event.target.value);
    }

    const ip = "http://10.79.168.241:8081/"


    const search = (event) => {
        if (event.key === 'Enter') {

            const _queryHistory = Array.isArray(JSON.parse(localStorage.getItem("queryHistory")))?JSON.parse(localStorage.getItem("queryHistory")):[]
            _queryHistory.push(query)
            setQueryHistory(_queryHistory)
            localStorage.setItem('queryHistory',JSON.stringify(_queryHistory))

            setHasSearch(true)
            const url = ip + `search?keywords=${query}`
            setSearchLoading(true)

            setSimilarResult([])
            setHasSimilarSearch(false)
            axios.get(url)
                .then((response) => {
                    setSearchResult(response.data)
                    setSearchLoading(false)
                })
                .catch((error) => {
                    console.log('error', error.response.data.message, error.message)
                })
        }
    }


    const getSimilarPage = (pageID) => {
        setHasSimilarSearch(true)
        setSimilarSearchLoading(true)
        const url = ip + `similar_search?pageID=${pageID}`
        axios.get(url)
            .then((response) => {
                setSimilarResult(response.data)
                setSearchLoading(false)
            })
            .catch((error) => {
                console.log('error', error.response.data.message, error.message)
            })
    }

    const getQueryHistory = () => {
        setOpen(true)
    }

    const queryHistoryList = Array.isArray(queryHistory) && queryHistory.length > 0?queryHistory.map((value, key) => {
        return <div className={"history-item"} key={key} onClick={() => {clickItem(value)}}>{value}</div>
    }):<div>no query history</div>


    const clickItem = (value) => {
        console.log(value)
        setOpen(false)
        setQuery(value)
        setHasSearch(true)
        const url = ip + `search?keywords=${query}`
        setSearchLoading(true)

        setSimilarResult([])
        setHasSimilarSearch(false)
        axios.get(url)
            .then((response) => {
                setSearchResult(response.data)
                setSearchLoading(false)
            })
            .catch((error) => {
                console.log('error', error.response.data.message, error.message)
            })
    }


    const listResult = Array.isArray(searchResult) && searchResult.length > 0?searchResult.map((value, key) => {
        return <Card className={"result-item"} loading={searchLoading} key={key}>
            <div><b>title:</b> <a href={value.url}>{value.title}</a></div>
            <div><b>score:</b> {value.score}</div>
            <div><b>url:</b>  <a href={value.url}>{value.url}</a></div>
            <div><b>last modified date:</b> {value.lastModifiedDate}</div>
            <div><b>size:</b> {value.size}</div>
            <div><b>keyword-frequency pairs: </b><br/>{value.freqList.map((v, k) => {
                return <span key={k}>({v.keyword}, {v.frequency}) </span>
            })}</div>
            <div><b>parent link:</b> {value.parentLinks.length?value.parentLinks.map((v, k) => {
                return <div key={k}><a href={v}>{v}</a></div>
            }): <div>no parent link</div>}</div>
            <div><b>child link:</b> {value.childLinks.length?value.childLinks.map((v, k) => {
                return <div key={k}><a href={v}>{v}</a></div>
            }): <div>no child link</div>}</div>
            <button onClick={() => {getSimilarPage(value.pageID)}} className={"button"}>Get Similar Pages</button>
        </Card>
    }):hasSearch?(searchLoading?<Spin/>:<div>no result</div>):null


    const similarListResult = Array.isArray(similarResult) && similarResult.length > 0?similarResult.map((value, key) => {
        return <Card className={"result-item"} loading={searchLoading} key={key}>
            <div><b>title:</b> <a href={value.url}>{value.title}</a></div>
            <div><b>score:</b> {value.score}</div>
            <div><b>url:</b>  <a href={value.url}>{value.url}</a></div>
            <div><b>last modified date:</b> {value.lastModifiedDate}</div>
            <div><b>size:</b> {value.size}</div>
            <div><b>keyword-frequency pairs: </b><br/>{value.freqList.map((v, k) => {
                return <span key={k}>({v.keyword}, {v.frequency}) </span>
            })}</div>
            <div><b>parent link:</b> {value.parentLinks.length?value.parentLinks.map((v, k) => {
                return <div key={k}><a href={v}>{v}</a></div>
            }): <div>no parent link</div>}</div>
            <div><b>child link:</b> {value.childLinks.length?value.childLinks.map((v, k) => {
                return <div key={k}><a href={v}>{v}</a></div>
            }): <div>no child link</div>}</div>
        </Card>
    }):hasSimilarSearch?(similarSearchLoading?<Spin/>:<div>no result</div>):null

    return (
        <div className={"main-body"}>
            <div className={"search-body"}>
                <div className={"logo"}>
                    <span className={"word blue"}>S</span>
                    <span className={"word red"}>e</span>
                    <span className={"word yellow"}>d</span>
                    <span className={"word blue"}>g</span>
                    <span className={"word green"}>l</span>
                    <span className={"word red"}>e</span>
                </div>
                <div className={"searchbar"}>
                    <SearchOutlined className={"mg"}/>
                    <input
                        value={query}
                        onChange={handleChange}
                        onFocus={() => {setHasFocus(true)}}
                        onBlur={() => {setHasFocus(false)}}
                        onKeyUp={(event) => search(event)}
                        id={"search"} />
                    {hasFocus && !!query && (
                        <div className='my-input-clear'
                             onMouseDown={(e) => {
                                 e.preventDefault()
                             }}
                             onClick={() => {
                                 setQuery('')
                             }}>
                            <CloseCircleOutlined />
                        </div>
                    )}
                </div>
                <button onClick={getQueryHistory} className={"button"}>Query History</button>
            </div>
            <div className={"result-body"}>
                <div className={"result-list"}>
                    {Array.isArray(searchResult) && searchResult.length > 0? <div className={"label"}>Search Result</div>: null}
                    {listResult}
                </div>

                <div className={"result-list"}>
                    {Array.isArray(similarResult) && similarResult.length > 0? <div className={"label"}>Similar Page Result</div>:null}
                    {similarListResult}
                </div>
            </div>
            <Drawer title="Query History" placement="right" onClose={() => {setOpen(false)}} open={open}>
                {queryHistoryList}
            </Drawer>

        </div>
    );
}
export default SearchPage;
