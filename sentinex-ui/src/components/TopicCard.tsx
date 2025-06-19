type Props = {
    topic: string,
    onClick: (topic: string) => void
}

export default function TopicCard({topic, onClick}: Props) {
    return (
        <button
            className="bg-blue-500 hover:bg-blue-600 text-white font-semibold px-4 py-2 rounded m-2 shadow"
            onClick={() => onClick(topic)}>

            {topic}
            
        </button>
    )
}